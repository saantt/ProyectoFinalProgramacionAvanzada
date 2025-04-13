package co.edu.uniquindio.proyectofinal.proyecto.controller;

import co.edu.uniquindio.proyectofinal.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.categoria.CategoriaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.services.CategoriaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categorias")
public class CategoriaControlller {

    private final CategoriaService categoriaServicio;

    // Crear una nueva categoría (solo administradores)
    @PostMapping
    public ResponseEntity<MensajeDTO<String>> crear(@Valid @RequestBody CategoriaDTO categoria) throws Exception {
        categoriaServicio.crearCategoria(categoria);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Categoría creada exitosamente"));
    }

    // Editar una categoría existente
    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>> editar(@PathVariable String id,
            @Valid @RequestBody CategoriaDTO categoria) throws Exception {
        categoriaServicio.editarCategoria(id, categoria);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Categoría actualizada correctamente"));
    }

    // Eliminar una categoría
    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminar(@PathVariable String id) throws Exception {
        categoriaServicio.eliminarCategoria(id);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Categoría eliminada correctamente"));
    }

    // Listar todas las categorías
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listar() {
        List<CategoriaDTO> categorias = categoriaServicio.listar();
        return ResponseEntity.ok(categorias);
    }
}