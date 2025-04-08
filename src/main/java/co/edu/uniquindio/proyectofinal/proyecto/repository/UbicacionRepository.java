package co.edu.uniquindio.proyectofinal.proyecto.repository;

import co.edu.uniquindio.proyectofinal.proyecto.model.Ubicacion;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UbicacionRepository extends MongoRepository<Ubicacion, String> {
}
