package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.dto.comentario.*;

import java.util.List;

public interface ComentarioService {
    String crearComentario(ComentarioCreacionDTO dto);
    List<ComentarioDTO> listarComentariosPorReporte(String idReporte);
}
