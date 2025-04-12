package co.edu.uniquindio.proyectofinal.proyecto.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record CambiarPasswordDTO(
        @NotBlank String codigoValidacion,
        @NotBlank @Email String email,
        @NotBlank @Length(min = 6) String nuevaPassword
) {
}