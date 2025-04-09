package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.dto.comentario.ComentarioCreacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.comentario.ComentarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Comentario;

import java.util.List;
import java.util.Optional;

/* public interface ComentarioService {

    Comentario guardar(Comentario comentario);

    Optional<Comentario> obtenerComentariosPorReporte(String idReporte);

    void crearComentario(ComentarioCreacionDTO dto);

    Object listarComentariosPorReporte(String idReporte);
} */

public interface ComentarioService {
    void crearComentario(ComentarioCreacionDTO dto);
    List<ComentarioDTO> listarComentariosPorReporte(String idReporte);
}

