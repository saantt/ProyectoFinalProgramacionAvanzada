package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniquindio.proyectofinal.proyecto.dto.estado.CambiarEstadoDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.estado.HistorialEStadoDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.notificacion.NotificacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.exception.ResourceNotFoundException;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoReporteEnum;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoUsuario;
import co.edu.uniquindio.proyectofinal.proyecto.repository.CategoriaRepository;
import co.edu.uniquindio.proyectofinal.proyecto.repository.ReporteRepository;
import co.edu.uniquindio.proyectofinal.proyecto.repository.UsuarioRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.NotificationService;
import co.edu.uniquindio.proyectofinal.proyecto.services.ReporteService;
import co.edu.uniquindio.proyectofinal.proyecto.util.ReporteMapper;

import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.*;
import co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion.UbicacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.*;
import co.edu.uniquindio.proyectofinal.proyecto.repository.ReporteRepository;
import co.edu.uniquindio.proyectofinal.proyecto.util.PDFGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository reporteRepositorio;
    private final UsuarioRepository usuarioRepositorio;
    private final ReporteMapper reporteMapper;
    private final WebSocketNotificationService webSocketNotificationService;
    private final NotificationService notificationService;

    public ReporteServiceImpl(ReporteRepository reporteRepositorio, UsuarioRepository usuarioRepositorio,
            ReporteMapper reporteMapper, WebSocketNotificationService webSocketNotificationService,
            CategoriaRepository categoriaRepository, NotificationService notificationService) {
        this.reporteRepositorio = reporteRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.reporteMapper = reporteMapper;
        this.webSocketNotificationService = webSocketNotificationService;
        this.categoriaRepository = categoriaRepository;
        this.notificationService = notificationService;
    }

    @Override
    public void crearReporte(ReporteCreacionDTO dto) {
        // Buscar usuario o lanzar excepción personalizada
        if (!ObjectId.isValid(dto.getClienteId())) {
            throw new IllegalArgumentException("ID de cliente no válido");
        }

        ObjectId clienteId = new ObjectId(dto.getClienteId());
        Usuario usuario = usuarioRepositorio.findById(clienteId)
                .filter(u -> u.getEstado() != EstadoUsuario.ELIMINADO)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado o inactivo"));

        // 2. Validar y obtener categoría
        ObjectId categoriaId = new ObjectId(dto.getCategoriaId());
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("La categoría no existe"));

        // 3. Crear y guardar reporte
        Reporte reporte = reporteMapper.toDocument(dto);
        reporte.setCategoriaId(categoriaId);
        reporte.setClienteId(clienteId); // Asegurar que tiene clienteId
        asignarDatosAdicionales(reporte);
        Reporte reporteGuardado = reporteRepositorio.save(reporte);

        // Enviar notificación por WebSocket
        try {
            notificationService.crearYEnviarNotificacion(
                    "Nuevo reporte creado",
                    String.format("Has creado un nuevo reporte: '%s' (Categoría: %s)",
                            reporteGuardado.getTitulo(),
                            categoria.getNombre()),
                    clienteId.toHexString(), // Convertir ObjectId a String
                    "CREACION", // Tipo de notificación para creación
                    true // Enviar email
            );
        } catch (Exception e) {
            System.err.println("ERROR al notificar creación de reporte: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void actualizarReporte(String id, EditarReporteDTO dto) throws Exception {
        // Validar que el ID tenga un formato correcto
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("El ID proporcionado no es válido: " + id);
        }

        ObjectId objectId = new ObjectId(id);

        // Buscar el reporte existente - Cambiar a ResourceNotFoundException
        Reporte reporteExistente = reporteRepositorio.findById(objectId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el reporte con ID: " + id));

        // Resto del código permanece igual...
        reporteMapper.EditarReporteDTO(dto, reporteExistente);
        reporteRepositorio.save(reporteExistente);

        NotificacionDTO notificacionDTO = new NotificacionDTO(
                "Reporte Actualizado",
                "Se ha actualizado el reporte: " + reporteExistente.getTitulo(),
                "reports");
        webSocketNotificationService.notificarClientes(notificacionDTO);
    }

    @Override
    @Transactional
    public void eliminarReporte(String id) {
        // 1. Validar ID
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("ID de reporte no válido: " + id);
        }

        // 2. Obtener reporte
        ObjectId reporteId = new ObjectId(id);
        Reporte reporte = reporteRepositorio.findById(reporteId)
                .orElseThrow(() -> new NoSuchElementException("Reporte no encontrado con id: " + id));

        // 3. Verificar que tenga clienteId
        if (reporte.getClienteId() == null) {
            throw new IllegalStateException("El reporte no tiene un cliente asociado");
        }

        // 4. Actualizar estado
        EstadoReporte estadoEliminado = new EstadoReporte();
        estadoEliminado.setEstado(EstadoReporteEnum.ELIMINADO);
        reporte.setEstadoActual(estadoEliminado);
        reporteRepositorio.save(reporte);

        // 5. Enviar notificación
        try {
            notificationService.crearYEnviarNotificacion(
                    "Reporte eliminado",
                    String.format("El reporte '%s' ha sido eliminado", reporte.getTitulo()),
                    reporte.getClienteId().toHexString(), // Convertir ObjectId a String
                    "ELIMINACION", // Tipo de notificación
                    true // Enviar email
            );
        } catch (Exception e) {
            System.err.println("ERROR al notificar eliminación de reporte: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<ReporteDTO> obtener() {
        List<Reporte> reportes = reporteRepositorio.findAll();
        return reportes.stream()
                .map(reporteMapper::toDTO)
                .toList();
    }

    @Override
    public void marcarImportante(String id) {
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("ID no válido: " + id);
        }

        Reporte reporte = reporteRepositorio.findById(new ObjectId(id))
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado con id: " + id));

        // Cambia entre 0 y 1
        int nuevoValor = (reporte.getContadorImportante() != null && reporte.getContadorImportante() == 1) ? 0 : 1;
        reporte.setContadorImportante(nuevoValor);
        reporteRepositorio.save(reporte);

        // Notificación WebSocket
        String mensaje = (nuevoValor == 1) ? "marcado como importante" : "desmarcado como importante";

        NotificacionDTO notificacionDTO = new NotificacionDTO(
                "Importancia actualizada",
                "El reporte '" + reporte.getTitulo() + "' fue " + mensaje,
                "reports");

        webSocketNotificationService.notificarClientes(notificacionDTO);
    }

    @Override
    public void cambiarEstadoReporte(String id, CambiarEstadoDTO cambiarEstadoDTO) {
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("ID no válido: " + id);
        }

        Reporte reporte = reporteRepositorio.findById(new ObjectId(id))
                .orElseThrow(() -> new ResourceNotFoundException("Reporte no encontrado"));

        // Actualizar el estado
        EstadoReporte nuevoEstado = new EstadoReporte();
        nuevoEstado.setEstado(cambiarEstadoDTO.getNuevoEstado());
        reporte.setEstadoActual(nuevoEstado);

        // Guardar cambios
        reporteRepositorio.save(reporte);

        // DEBUG: Verificar usuarioId
        String estadoAnterior = reporte.getEstadoActual().getEstado().name();
        String clienteIdString = reporte.getClienteId().toHexString();
        System.out.println("ClienteId (ObjectId): " + reporte.getClienteId());
        System.out.println("ClienteId (String): " + clienteIdString);

        // Enviar notificación (versión mejorada)
        try {
            notificationService.crearYEnviarNotificacion(
                    "Estado de reporte actualizado",
                    String.format("El estado de tu reporte '%s' ha cambiado de %s a %s",
                            reporte.getTitulo(),
                            estadoAnterior,
                            cambiarEstadoDTO.getNuevoEstado()),
                    clienteIdString, // Enviamos el ID como String
                    cambiarEstadoDTO.getNuevoEstado().name(),
                    true);
        } catch (Exception e) {
            System.err.println("ERROR al enviar notificación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public InfoReporteDTO obtenerReporte(String id) throws Exception {
        return null;
    }

    @Override
    public List<InfoReporteDTO> obtenerReportes(String categoria, EstadoReporte estadoReporte, int pagina)
            throws Exception {
        return List.of();
    }

    @Override
    public List<InfoReporteDTO> obtenerReportesUsuario(String idusuario, int pagina) throws Exception {
        return List.of();
    }

    @Override
    public List<InfoReporteDTO> obtenerReportes(Ubicacion ubicacion) throws Exception {
        return List.of();
    }

    @Override
    public List<HistorialEStadoDTO> listarHistorialEstados(String id) throws Exception {
        return List.of();
    }

    private Usuario obtenerUsuarioActivo(String clienteId) {
        return usuarioRepositorio.findById(new ObjectId(clienteId))
                .filter(usuario -> usuario.getEstado() != EstadoUsuario.ELIMINADO)
                .orElseThrow(() -> new RuntimeException("El usuario no existe o está inactivo"));
    }

    private void asignarDatosAdicionales(Reporte reporte) {
        reporte.setEstadoActual(EstadoReporte.PENDIENTE);
        reporte.setFecha(LocalDateTime.now());
        reporte.setContadorImportante(0);
    }

    private void notificarNuevoReporte(Reporte reporte) {
        NotificacionDTO notificacionDTO = new NotificacionDTO(
                "Nuevo Reporte",
                "Se acaba de crear un nuevo reporte: " + reporte.getTitulo(),
                "reports");
        webSocketNotificationService.notificarClientes(notificacionDTO);
    }

    private final CategoriaRepository categoriaRepository;

    @Override
    public InformeGeneradoDTO generarInforme(FiltroInformeDTO filtro) {
        List<Reporte> reportesFiltrados = filtrarReportes(filtro);

        // Versión corregida con paréntesis balanceados
        Map<String, Integer> reportesPorCategoria = reportesFiltrados.stream()
                .collect(Collectors.groupingBy(
                        r -> {
                            Categoria categoria = categoriaRepository.findById(r.getCategoriaId())
                                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
                            // .orElse(new Categoria("Desconocida"));
                            return categoria.getNombre();
                        },
                        Collectors.summingInt(r -> 1)));

        Map<String, Integer> reportesPorEstado = reportesFiltrados.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getEstadoActual().getEstado().name(),
                        Collectors.summingInt(r -> 1)));

        int totalImportantes = (int) reportesFiltrados.stream()
                .filter(r -> r.getContadorImportante() == 1)
                .count();

        List<ReporteInformeDTO> reportesDTO = reportesFiltrados.stream()
                .map(this::convertirAReporteInformeDTO)
                .collect(Collectors.toList());

        return new InformeGeneradoDTO(
                "Informe de Reportes",
                LocalDateTime.now(),
                "Del " + filtro.fechaInicio() + " al " + filtro.fechaFin(),
                reportesFiltrados.size(),
                reportesDTO,
                new EstadisticasInformeDTO(
                        reportesFiltrados.size(),
                        totalImportantes,
                        reportesPorCategoria,
                        reportesPorEstado));
    }

    @Override
    public byte[] generarInformePDF(FiltroInformeDTO filtro) {
        InformeGeneradoDTO informe = generarInforme(filtro);
        return PDFGenerator.generarInformePDF(informe);
    }

    private List<Reporte> filtrarReportes(FiltroInformeDTO filtro) {
        // Esto automáticamente usará la implementación de MongoTemplate
        return reporteRepositorio.findByFilters(
                filtro.categoriaId(), // String
                filtro.sector(), // String
                filtro.fechaInicio(), // LocalDateTime
                filtro.fechaFin() // LocalDateTime
        );
    }

    private ReporteInformeDTO convertirAReporteInformeDTO(Reporte reporte) {
        // Obtener el nombre de la categoría (necesitarás inyectar CategoriaRepository)
        String nombreCategoria = categoriaRepository.findById(reporte.getCategoriaId())
                .map(Categoria::getNombre)
                .orElse("Desconocida");

        return new ReporteInformeDTO(
                reporte.getTitulo(),
                reporte.getDescripcion(),
                reporte.getFecha(),
                new UbicacionDTO(reporte.getUbicacion().getLatitud(), reporte.getUbicacion().getLongitud()),
                nombreCategoria,
                reporte.getContadorImportante() == 1,
                reporte.getEstadoActual().getEstado().name());
    }

}