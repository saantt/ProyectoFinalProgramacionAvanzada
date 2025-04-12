package co.edu.uniquindio.proyectofinal.proyecto.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ActivarCuentaDTO(
        @NotBlank(        
        message = "El codigo de validación es obligatorio")
        String codigoValidacion,
        @NotBlank 
        @Email String email
) {}
