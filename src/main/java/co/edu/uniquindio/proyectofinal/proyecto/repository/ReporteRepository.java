package co.edu.uniquindio.proyectofinal.proyecto.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import co.edu.uniquindio.proyectofinal.proyecto.model.Reporte;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReporteRepository extends MongoRepository<Reporte, ObjectId> {

    
    List<Reporte> findByClienteId(ObjectId usuarioId);

    
    List<Reporte> findByEstadoActual(String estadoActual);

    
    List<Reporte> findByTituloContainingIgnoreCase(String titulo);

    
    List<Reporte> findByCategoriaId(ObjectId categoriaId);
}