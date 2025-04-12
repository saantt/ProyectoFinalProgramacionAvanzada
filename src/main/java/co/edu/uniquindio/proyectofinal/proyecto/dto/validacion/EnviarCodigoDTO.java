package co.edu.uniquindio.proyectofinal.proyecto.dto.validacion;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EnviarCodigoDTO(
        @NotBlank @Email String email
) {}
