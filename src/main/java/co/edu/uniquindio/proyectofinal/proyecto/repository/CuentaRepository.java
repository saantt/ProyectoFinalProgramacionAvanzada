package co.edu.uniquindio.proyectofinal.proyecto.repository;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.proyectofinal.proyecto.model.Cuenta;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends MongoRepository<Cuenta, String> {

    @Query("{ 'email' : ?0 }")
    Optional<Cuenta> buscaremail(String email);

    @Query("{ 'usuario.cedula' : ?0 }")
    Optional<Cuenta> buscarCedula(String cedula);

    @Query("{email: ?0, password: ?1 }")
    Optional<Cuenta> validarDatosAutenticacion(String email, String password);

    Optional<Cuenta> findByEmail(String email);

    @Query("{ 'codigoValidacionRegistro.codigo': ?0 }")
    Optional<Cuenta> buscarPorCodigoValidacion(String token);

    @Query("{ 'reportes.titulo' : ?0 }")
    List<Cuenta> buscarReportesPorTitulo(String tituloReporte);


}