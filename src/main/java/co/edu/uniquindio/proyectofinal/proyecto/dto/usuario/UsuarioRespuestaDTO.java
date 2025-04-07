package co.edu.uniquindio.proyectofinal.proyecto.dto.usuario;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioRespuestaDTO {
    private String token;
    private UsuarioDTO usuario;
}
