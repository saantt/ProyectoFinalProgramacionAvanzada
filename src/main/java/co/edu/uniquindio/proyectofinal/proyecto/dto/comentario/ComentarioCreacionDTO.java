package co.edu.uniquindio.proyectofinal.proyecto.dto.comentario;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComentarioCreacionDTO {
    private String contenido;
    private String idUsuario;
    private String idReporte;
}
