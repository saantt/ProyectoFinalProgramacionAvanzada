package co.edu.uniquindio.proyectofinal.proyecto.dto.comentario;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComentarioDTO {
    private String id;
    private String contenido;
    private String idUsuario;
    private String idReporte;
    private LocalDateTime fechaComentario;
}
