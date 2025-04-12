package co.edu.uniquindio.proyectofinal.proyecto.repository;

import jakarta.validation.constraints.NotBlank;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.proyectofinal.proyecto.model.Categoria;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends MongoRepository<Categoria, ObjectId> {
    
    Optional<Categoria> findByNombre(String nombre);
    
    boolean existsByNombre(String nombre);

    boolean existsByNombreIgnoreCase(@NotBlank(message = "El nombre es obligatorio") String nombre);
}