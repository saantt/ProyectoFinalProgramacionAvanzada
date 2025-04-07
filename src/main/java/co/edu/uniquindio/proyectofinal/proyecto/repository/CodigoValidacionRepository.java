package co.edu.uniquindio.proyectofinal.proyecto.repository;

import co.edu.uniquindio.proyectofinal.proyecto.model.CodigoValidacion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CodigoValidacionRepository extends MongoRepository<CodigoValidacion, String> {
    Optional<CodigoValidacion> findByEmail(String email);
}
