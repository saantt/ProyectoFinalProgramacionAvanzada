package co.edu.uniquindio.proyectofinal.proyecto.dto.notificacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public record NotificacionDTO(
        String titulo,
        String cuerpo,
        String topic
) {}
