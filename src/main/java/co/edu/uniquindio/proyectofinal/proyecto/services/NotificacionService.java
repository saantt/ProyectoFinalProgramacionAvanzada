package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.dto.notificacion.*;

import java.util.List;

public interface NotificacionService {
    void enviarNotificacion(NotificacionDTO dto);
    List<NotificacionRespuestaDTO> listarNotificaciones(String idUsuario);
}
