package co.edu.uniquindio.proyectofinal.proyecto.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.proyectofinal.proyecto.model.Notificacion;

import java.util.List;

@Repository
public interface NotificacionRepository extends MongoRepository<Notificacion, String> {

    List<Notificacion> findByUsuarioIdOrderByFechaCreacionDesc(String usuarioId);

    List<Notificacion> findBySectorAndLeidaFalse(String sector);

    int countByUsuarioIdAndLeidaFalse(String usuarioId);

}