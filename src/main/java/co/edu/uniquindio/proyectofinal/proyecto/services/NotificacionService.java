package co.edu.uniquindio.proyectofinal.proyecto.services;

import java.util.List;

import co.edu.uniquindio.proyectofinal.proyecto.dto.notificacion.NotificacionCreacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.notificacion.NotificacionDTO;

public interface NotificacionService {
    void enviarNotificacion(NotificacionCreacionDTO dto);

    List<NotificacionDTO> listarNotificacionesUsuario(String idUsuario);

    Object obtenerNotificaciones(String idUsuario);
}
