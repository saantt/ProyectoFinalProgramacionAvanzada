package co.edu.uniquindio.proyectofinal.proyecto.dto.notificacion;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificacionRespuestaDTO {
    private String mensaje;
    private String fechaEnvio;
    private boolean leido;
}
