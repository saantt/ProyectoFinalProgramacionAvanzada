package co.edu.uniquindio.proyectofinal.proyecto.dto.usuario;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioRegistroDTO {
    private String nombre;
    private String email;
    private String password;
}
