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
@Tag(name = "Comentarios")
public class ComentarioController {

    private final ComentarioService comentarioService;

    @PostMapping
    public ResponseEntity<Void> crearComentario(@RequestBody ComentarioCreacionDTO dto) {
        comentarioService.crearComentario(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reporte/{idReporte}")
    public ResponseEntity<Object> listarComentariosPorReporte(@PathVariable String idReporte) {
        return ResponseEntity.ok(comentarioService.listarComentariosPorReporte(idReporte));
    }
}
