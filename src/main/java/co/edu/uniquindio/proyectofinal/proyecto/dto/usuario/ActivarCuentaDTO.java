package co.edu.uniquindio.proyectofinal.proyecto.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ActivarCuentaDTO(
        @NotBlank(message = "El token de activación es obligatorio.")
        String token,

        @NotBlank(message = "El email electrónico es obligatorio.")
        @Email(message = "El email electrónico debe tener un formato válido.")
        String email

) {
}
