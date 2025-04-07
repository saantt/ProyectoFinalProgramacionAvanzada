package co.edu.uniquindio.proyectofinal.proyecto.dto.validacion;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificacionCodigoDTO {
    private String idUsuario;
    private String codigoIngresado;
}
