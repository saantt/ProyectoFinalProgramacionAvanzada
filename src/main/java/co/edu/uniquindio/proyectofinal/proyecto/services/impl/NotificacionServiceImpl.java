/* package co.edu.uniquindio.proyectofinal.proyecto.services.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.proyectofinal.proyecto.dto.notificacion.NotificacionCreacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.notificacion.NotificacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Notificacion;
import co.edu.uniquindio.proyectofinal.proyecto.repository.NotificacionRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.NotificacionService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionRepository notificacionRepository;

    @Override
    public void enviarNotificacion(NotificacionCreacionDTO dto) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setId(dto.getIdUsuario());
        notificacion.setFechaCreacion(LocalDateTime.now());

        notificacionRepository.save(notificacion);
    }

    @Override
    public List<NotificacionDTO> listarNotificacionesUsuario(String idUsuario) {
        return notificacionRepository.findById(idUsuario)
                .stream()
                .map(n -> new NotificacionDTO(
                        n.getId(),
                        n.getMensaje(),
                        n.getId(),
                        n.getFechaCreacion()
                )).collect(Collectors.toList());
    }

    @Override
    public Object obtenerNotificaciones(String idUsuario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerNotificaciones'");
    }
}
 */