package co.edu.uniquindio.proyectofinal.proyecto.dto.usuario;

import lombok.*;

public record UsuarioDTO (
        String id,
        String nombre,
        String email,
        String telefono,
        String ciudad,
        String estado,
        String rol,
        String fechaRegistro
){}