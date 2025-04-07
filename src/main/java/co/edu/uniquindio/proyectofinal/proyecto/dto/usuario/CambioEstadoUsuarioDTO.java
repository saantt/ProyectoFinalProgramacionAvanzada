package co.edu.uniquindio.proyectofinal.proyecto.dto.usuario;

import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoUsuario;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CambioEstadoUsuarioDTO {
    private String idUsuario;
    private EstadoUsuario nuevoEstado;
}
