package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.proyectofinal.proyecto.dto.estado.CambiarEstadoDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.estado.HistorialEStadoDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.notificacion.NotificacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.exception.ResourceNotFoundException;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoReporteEnum;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoUsuario;
import co.edu.uniquindio.proyectofinal.proyecto.repository.CategoriaRepository;
import co.edu.uniquindio.proyectofinal.proyecto.repository.ReporteRepository;
import co.edu.uniquindio.proyectofinal.proyecto.repository.UsuarioRepository;
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

    public ReporteServiceImpl(ReporteRepository reporteRepositorio, UsuarioRepository usuarioRepositorio,
            ReporteMapper reporteMapper, WebSocketNotificationService webSocketNotificationService,
            CategoriaRepository categoriaRepository) {
        this.reporteRepositorio = reporteRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.reporteMapper = reporteMapper;
        this.webSocketNotificationService = webSocketNotificationService;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void crearReporte(ReporteCreacionDTO dto) {
        // Buscar usuario o lanzar excepción personalizada
        Usuario usuario = obtenerUsuarioActivo(dto.getClienteId());

        ObjectId categoriaId = new ObjectId(dto.getCategoriaId());

        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new RuntimeException("La categoría no existe"));

        Reporte reporte = reporteMapper.toDocument(dto);
        reporte.setCategoriaId(categoriaId);

        asignarDatosAdicionales(reporte);
        // Guardar el reporte en la base de datos
        reporteRepositorio.save(reporte);
        // Enviar notificación por WebSocket
        notificarNuevoReporte(reporte);
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
    public void eliminarReporte(String id) {
        // Validar el ID
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("El ID proporcionado no es válido: " + id);
        }
        // Buscar el reporte por ID
        ObjectId objectId = new ObjectId(id);
        Optional<Reporte> optionalReporte = reporteRepositorio.findById(objectId);
        if (optionalReporte.isEmpty()) {
            throw new NoSuchElementException("No se encontró un reporte con el id: " + id);
        }
        Reporte reporte = optionalReporte.get();
        reporte.setEstadoActual(EstadoReporte.ELIMINADO);

        reporteRepositorio.save(reporte);
        // Notificar por WebSocket que se eliminó un reporte (opcional, pero
        // recomendable)
        NotificacionDTO notificacionDTO = new NotificacionDTO(
                "Reporte Eliminado",
                "Se ha eliminado el reporte: " + reporte.getTitulo(),
                "reports");
        webSocketNotificationService.notificarClientes(notificacionDTO);
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

        // Notificación opcional
        NotificacionDTO notificacion = new NotificacionDTO(
                "Estado actualizado",
                "El estado del reporte " + reporte.getTitulo() + " ha cambiado a " + cambiarEstadoDTO.getNuevoEstado(),
                "reports");
        webSocketNotificationService.notificarClientes(notificacion);
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