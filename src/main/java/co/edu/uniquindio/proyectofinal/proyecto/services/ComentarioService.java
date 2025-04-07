package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.model.Comentario;

import java.util.List;
import java.util.Optional;

public interface ComentarioService {

    Comentario guardar(Comentario comentario);

    Optional<Comentario> obtenerComentariosPorReporte(String idReporte);
}
