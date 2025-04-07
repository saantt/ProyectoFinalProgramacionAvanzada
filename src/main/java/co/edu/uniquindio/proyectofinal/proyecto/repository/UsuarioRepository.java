package co.edu.uniquindio.proyectofinal.proyecto.repository;

import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
}
