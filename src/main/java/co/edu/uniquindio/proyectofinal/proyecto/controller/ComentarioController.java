package co.edu.uniquindio.proyectofinal.proyecto.controller;

import co.edu.uniquindio.proyectofinal.proyecto.dto.comentario.ComentarioCreacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.comentario.ComentarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.services.ComentarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comentarios")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioServicio;

    @PostMapping("/{idReporte}")
    public void crearComentario(@PathVariable String idReporte, @RequestBody ComentarioCreacionDTO comentarioDTO) throws Exception {
        comentarioServicio.crearComentario(idReporte, comentarioDTO);
    }

    @PutMapping("/{idReporte}/{idComentario}")
    public ResponseEntity<String> editarComentario(
            @PathVariable String idReporte,
            @PathVariable String idComentario,
            @RequestParam String nuevoMensaje
    ) throws Exception {
        comentarioServicio.editarComentario(idReporte, idComentario, nuevoMensaje);
        return ResponseEntity.ok("Comentario actualizado correctamente");
    }

    @GetMapping("/{idReporte}")
    public List<ComentarioDTO> obtenerComentarios(@PathVariable String idReporte) throws Exception {
        return comentarioServicio.obtenerComentarios(idReporte);
    }
}