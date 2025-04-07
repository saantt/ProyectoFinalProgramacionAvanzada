package co.edu.uniquindio.proyectofinal.proyecto.dto.usuario;

import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoUsuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.Rol;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
    private String id;
    private String nombre;
    private String email;
    private Rol rol;
    private EstadoUsuario estado;
}
