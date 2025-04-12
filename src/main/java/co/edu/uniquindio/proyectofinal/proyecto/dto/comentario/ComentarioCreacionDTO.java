package co.edu.uniquindio.proyectofinal.proyecto.dto.comentario;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComentarioCreacionDTO {
    @NotBlank(message = "El mensaje es obligatorio")
    String mensaje;

    @NotBlank(message = "El ID del cliente es obligatorio")
    String clienteId;


{
}
}