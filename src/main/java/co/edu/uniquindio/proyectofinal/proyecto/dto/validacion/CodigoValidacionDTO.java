package co.edu.uniquindio.proyectofinal.proyecto.dto.validacion;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CodigoValidacionDTO {
    private String idUsuario;
    private String codigo;
    private String fechaExpiracion;
}
