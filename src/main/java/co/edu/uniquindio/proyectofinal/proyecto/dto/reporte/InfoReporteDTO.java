package co.edu.uniquindio.proyectofinal.proyecto.dto.reporte;

import lombok.*;

import java.time.LocalDateTime;

public record InfoReporteDTO (
        String id,
        String titulo,
        String descripcion,
        String categoria,
        String ciudad,
        String estado,
        LocalDateTime fechaCreacion
){}
