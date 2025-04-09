package co.edu.uniquindio.proyectofinal.proyecto.dto.usuario;

import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
    private String id;
    private String nombre;
    private String correo;
    private String password;
    private Rol rol;
}


