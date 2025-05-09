package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.proyectofinal.proyecto.dto.estado.CambiarEstadoDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.estado.HistorialEStadoDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.notificacion.NotificacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.EditarReporteDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.InfoReporteDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.ReporteCreacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.ReporteDTO;
import co.edu.uniquindio.proyectofinal.proyecto.exception.ResourceNotFoundException;
import co.edu.uniquindio.proyectofinal.proyecto.model.EstadoReporte;
import co.edu.uniquindio.proyectofinal.proyecto.model.Reporte;
import co.edu.uniquindio.proyectofinal.proyecto.model.Ubicacion;
import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoReporteEnum;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoUsuario;
import co.edu.uniquindio.proyectofinal.proyecto.repository.ReporteRepository;
import co.edu.uniquindio.proyectofinal.proyecto.repository.UsuarioRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.ReporteService;
import co.edu.uniquindio.proyectofinal.proyecto.util.ReporteMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository reporteRepositorio;
    private final UsuarioRepository usuarioRepositorio;
    private final ReporteMapper reporteMapper;
    private final WebSocketNotificationService webSocketNotificationService;

    public ReporteServiceImpl(ReporteRepository reporteRepositorio, UsuarioRepository usuarioRepositorio,
            ReporteMapper reporteMapper, WebSocketNotificationService webSocketNotificationService) {
        this.reporteRepositorio = reporteRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.reporteMapper = reporteMapper;
        this.webSocketNotificationService = webSocketNotificationService;
    }

    @Override
    public void crearReporte(ReporteCreacionDTO dto) {
        // Buscar usuario o lanzar excepción personalizada
        Usuario usuario = obtenerUsuarioActivo(dto.getClienteId());
        // Crear el documento Reporte a partir del DTO
        Reporte reporte = reporteMapper.toDocument(dto);
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
    public ReporteDTO obtener(String id) {
        // Validar que el ID tenga formato correcto
        if (!ObjectId.isValid(id)) {
            throw new IllegalArgumentException("El ID proporcionado no es válido: " + id);
        }
        ObjectId objectId = new ObjectId(id);
        // Buscar el reporte por ID
        Reporte reporte = reporteRepositorio.findById(objectId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró un reporte con el id: " + id));
        // Convertir el documento a DTO usando el mapper
        return reporteMapper.toDTO(reporte);
    }

    @Override
    public void marcarImportante(String id) {

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
}