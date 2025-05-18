package co.edu.uniquindio.proyectofinal.proyecto.dto.reporte;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public record FiltroInformeDTO(
        String categoriaId,
        String sector,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
}