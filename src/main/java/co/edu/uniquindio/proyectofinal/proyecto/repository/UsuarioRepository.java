package co.edu.uniquindio.proyectofinal.proyecto.repository;

import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    Optional<Usuario> findByCorreo(Object correo);

    boolean existsByCorreo(String correo);
}
