package co.edu.uniquindio.proyectofinal.proyecto.repository;

import org.bson.types.ObjectId;

import co.edu.uniquindio.proyectofinal.proyecto.model.Reporte;

import java.time.LocalDateTime;
import java.util.List;

public interface ReporteRepositoryCustom {
    List<Reporte> findByFilters(String categoriaId, String sector,
            LocalDateTime fechaInicio, LocalDateTime fechaFin);
}