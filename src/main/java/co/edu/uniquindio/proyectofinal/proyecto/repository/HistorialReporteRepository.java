package co.edu.uniquindio.proyectofinal.proyecto.repository;

import co.edu.uniquindio.proyectofinal.proyecto.model.HistorialReporte;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HistorialReporteRepository extends MongoRepository<HistorialReporte, String> {
    List<HistorialReporte> findByReporteId(String reporteId);
}
