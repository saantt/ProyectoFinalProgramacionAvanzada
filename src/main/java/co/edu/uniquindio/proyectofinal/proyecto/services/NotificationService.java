package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.dto.notificacion.NotificacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Notificacion;

public interface NotificationService {
    void enviarNotificacion(Notificacion notificacion);

    void crearYEnviarNotificacion(String titulo, String mensaje, String usuarioId,
            String tipoNotificacion, boolean enviarEmail);
}