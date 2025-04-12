package co.edu.uniquindio.proyectofinal.proyecto.repository;

import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByemail(Object email);

    boolean existsByemail(String email);
}
