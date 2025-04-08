package co.edu.uniquindio.proyectofinal.proyecto.dto.usuario;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioLoginDTO {
    private String correo;
    private String password;
}
