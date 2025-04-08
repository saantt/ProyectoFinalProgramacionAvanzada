package co.edu.uniquindio.proyectofinal.proyecto.repository;

import co.edu.uniquindio.proyectofinal.proyecto.model.EstadoReporte;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EstadoReporteRepository extends MongoRepository<EstadoReporte, String> {
    List<EstadoReporte> findByReporteId(String reporteId);
}
