package co.edu.uniquindio.proyectofinal.proyecto.repository;

import co.edu.uniquindio.proyectofinal.proyecto.model.Comentario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ComentarioRepository extends MongoRepository<Comentario, String> {
    List<Comentario> findByReporteId(String reporteId);
}
