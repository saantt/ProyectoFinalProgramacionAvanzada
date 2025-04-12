package co.edu.uniquindio.proyectofinal.proyecto.repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoUsuario;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, ObjectId> {

    Optional<Usuario> findByEmail(String email);

    List<Usuario> findByNombreContaining(String nombre);

    List<Usuario> findByCiudad(String ciudad);

    List<Usuario> findByNombreContainingAndCiudad(String nombre, String ciudad);

    List<Usuario> findByNombreContainingIgnoreCaseAndEstado(String nombre, EstadoUsuario estado, Pageable pageable);

    List<Usuario> findByCiudadContainingIgnoreCaseAndEstado(String ciudad, EstadoUsuario estado, Pageable pageable);

    List<Usuario> findByNombreContainingIgnoreCaseAndCiudadContainingIgnoreCaseAndEstado(
            String nombre, String ciudad, EstadoUsuario estado, Pageable pageable);

    boolean existsByEmail(String email);
}
