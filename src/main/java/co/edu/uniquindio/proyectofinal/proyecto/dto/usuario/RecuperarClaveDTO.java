package co.edu.uniquindio.proyectofinal.proyecto.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RecuperarClaveDTO(

        @NotBlank @Email @Length(max = 100) String email
) {
}