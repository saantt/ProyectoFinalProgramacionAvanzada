package co.edu.uniquindio.proyectofinal.proyecto.dto.notificacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import co.edu.uniquindio.proyectofinal.proyecto.model.Notificacion;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificacionDTO {
    private String id;
    private String title;
    private String message;
    private String reportId;
    private String sector;
    private LocalDateTime timestamp;
    private boolean read;
    private String tipoNotificacion;
    private String usuarioEmail; // Nuevo campo necesario para el envío de emails
    private boolean enviarEmail;

    public static NotificacionDTO fromEntity(Notificacion notificacion) {
        return NotificacionDTO.builder()
                .id(notificacion.getId())
                .title(notificacion.getTitulo())
                .message(notificacion.getMensaje())
                .reportId(notificacion.getReporteId())
                .sector(notificacion.getSector())
                .timestamp(notificacion.getFechaCreacion())
                .read(notificacion.isLeida())
                .tipoNotificacion(notificacion.getTipoNotificacion())
                .usuarioEmail(notificacion.getUsuarioEmail())
                .enviarEmail(notificacion.isEnviarEmail())
                .build();
    }

    // Constructor para notificaciones rápidas
    public NotificacionDTO(String title, String message, String tipoNotificacion) {
        this.title = title;
        this.message = message;
        this.tipoNotificacion = tipoNotificacion;
        this.timestamp = LocalDateTime.now();
        this.read = false;
    }
}