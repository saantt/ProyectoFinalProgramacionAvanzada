package co.edu.uniquindio.proyectofinal.proyecto.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import co.edu.uniquindio.proyectofinal.proyecto.model.Comentario;

import java.util.List;

public interface ComentarioRepository extends MongoRepository<Comentario, ObjectId> {

    List<Comentario> findByReporteId(ObjectId reporteId);

    List<Comentario> findByUsuarioId(ObjectId usuarioId);

    void deleteByReporteId(ObjectId reporteId);

    long countByReporteId(ObjectId reporteId);
}
