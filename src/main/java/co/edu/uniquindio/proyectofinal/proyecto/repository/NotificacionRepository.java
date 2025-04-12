package co.edu.uniquindio.proyectofinal.proyecto.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.proyectofinal.proyecto.model.Notificacion;

import java.util.List;

@Repository
public interface NotificacionRepository extends MongoRepository<Notificacion, ObjectId> {
    
    List<Notificacion> findByUsuarioId(ObjectId usuarioId);
    
    List<Notificacion> findByUsuarioIdAndLeida(ObjectId usuarioId, boolean leida);
    
    long countByUsuarioIdAndLeida(ObjectId usuarioId, boolean leida);
}