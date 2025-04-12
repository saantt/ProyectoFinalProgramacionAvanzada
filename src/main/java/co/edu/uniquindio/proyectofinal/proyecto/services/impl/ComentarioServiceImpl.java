package co.edu.uniquindio.proyectofinal.proyecto.services.impl; 

import co.edu.uniquindio.proyectofinal.proyecto.dto.comentario.ComentarioCreacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.comentario.ComentarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Comentario;
import co.edu.uniquindio.proyectofinal.proyecto.repository.ComentarioRepository;
import co.edu.uniquindio.proyectofinal.proyecto.repository.ReporteRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.ComentarioService;
import co.edu.uniquindio.proyectofinal.proyecto.util.ComentarioMapper;
import lombok.RequiredArgsConstructor;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.bson.types.ObjectId;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {

    private final ReporteRepository reporteRepositorio;

    private final ComentarioMapper comentarioMapper;

    private final ComentarioRepository comentarioRepositorio;

   

    @Override
    public void editarComentario(String idReporte, String idComentario, String nuevoMensaje) throws Exception {
        // Validar IDs
        if (!ObjectId.isValid(idReporte) || !ObjectId.isValid(idComentario)) {
            throw new IllegalArgumentException("El ID del reporte o del comentario no es válido");
        }
        ObjectId reporteObjectId = new ObjectId(idReporte);
        ObjectId comentarioObjectId = new ObjectId(idComentario);
        // Verificar que el reporte exista
        reporteRepositorio.findById(reporteObjectId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró un reporte con el id: " + idReporte));
        // Buscar el comentario por ID
        Comentario comentario = comentarioRepositorio.findById(comentarioObjectId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró un comentario con el id: " + idComentario));
        // Verificar que el comentario pertenezca al reporte correcto
        if (!comentario.getReporteId().equals(reporteObjectId)) {
            throw new IllegalArgumentException("El comentario no pertenece al reporte indicado");
        }
        // Obtener usuario autenticado
        org.springframework.security.core.Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String usuarioAutenticadoId = authentication.getName();
        // Validar que el usuario autenticado sea el creador del comentario
        if (!comentario.getUsuarioId().toHexString().equals(usuarioAutenticadoId)) {
            throw new IllegalAccessException("No tienes permiso para editar este comentario");
        }
        // Actualizar mensaje
        comentario.setMensaje(nuevoMensaje);
        // Guardar cambios
        comentarioRepositorio.save(comentario);
    }

    @Override
    public List<ComentarioDTO> obtenerComentarios(String idReporte) throws Exception {
        // Validar que el ID del reporte tenga formato correcto
        if (!ObjectId.isValid(idReporte)) {
            throw new IllegalArgumentException("El ID del reporte no es válido: " + idReporte);
        }
        ObjectId objectId = new ObjectId(idReporte);
        // Verificar que el reporte exista
        reporteRepositorio.findById(objectId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró un reporte con el id: " + idReporte));
        // Consultar los comentarios directamente en la colección de comentarios
        List<Comentario> comentarios = comentarioRepositorio.findByReporteId(objectId);
        // Mapear los comentarios a DTO y devolverlos
        return comentarios.stream()
                .map(comentarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void crearComentario(String idReporte, ComentarioCreacionDTO comentarioDTO) throws Exception {
         // Validar que el ID del reporte tenga formato correcto
         if (!ObjectId.isValid(idReporte)) {
            throw new IllegalArgumentException("El ID del reporte no es válido: " + idReporte);
        }
        ObjectId objectId = new ObjectId(idReporte);
        // Verificar que el reporte exista
        reporteRepositorio.findById(objectId)
                .orElseThrow(() -> new NoSuchElementException("No se encontró un reporte con el id: " + idReporte));
        // Mapear CrearComentarioDTO a Comentario utilizando el mapper
        Comentario comentario = comentarioMapper.toDocument(comentarioDTO);
        // Generar un nuevo ID, establecer fecha actual y asignar el reporteId
        comentario.setId(new ObjectId());
        comentario.setFecha(LocalDateTime.now());
        comentario.setReporteId(objectId);
        // Guardar el comentario directamente en la colección de comentarios
        comentarioRepositorio.save(comentario);
    }
}
