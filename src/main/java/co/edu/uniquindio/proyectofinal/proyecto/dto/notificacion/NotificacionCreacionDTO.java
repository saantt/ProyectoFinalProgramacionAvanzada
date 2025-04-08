package co.edu.uniquindio.proyectofinal.proyecto.dto.notificacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionCreacionDTO {
    private String mensaje;
    private String idUsuario;
}
