package co.edu.uniquindio.proyectofinal.proyecto.dto.validacion;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record ValidarCodigoDTO (
        @NotBlank(message = "El email es obligatorio")
        @Email(message = "El formato del email no es válido")
        String email,

        @NotBlank(message = "El código es obligatorio")
        String codigo
){}
