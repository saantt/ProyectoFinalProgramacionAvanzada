package co.edu.uniquindio.proyectofinal.proyecto.dto.usuario;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public record EditarCuentaDTO(
        @NotBlank String id,
        @NotBlank @Length(max = 50) String nombre,
        @NotBlank @Length(max = 10) String telefono,
        @Length(max = 100) String direccion
) {
}