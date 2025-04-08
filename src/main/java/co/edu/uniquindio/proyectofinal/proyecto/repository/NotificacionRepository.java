package co.edu.uniquindio.proyectofinal.proyecto.repository;

import co.edu.uniquindio.proyectofinal.proyecto.model.Notificacion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificacionRepository extends MongoRepository<Notificacion, String> {
    List<Notificacion> findByUsuarioId(String usuarioId);
    
}
