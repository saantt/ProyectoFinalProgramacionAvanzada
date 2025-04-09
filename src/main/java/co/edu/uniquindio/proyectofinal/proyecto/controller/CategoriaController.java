    package co.edu.uniquindio.proyectofinal.proyecto.controller;

import co.edu.uniquindio.proyectofinal.proyecto.dto.categoria.CategoriaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.services.CategoriaService;
import io.swagger.v3.oas.annotations.tags.Tag;                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;  




@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorías")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<Object> obtenerCategorias() {
        return ResponseEntity.ok(categoriaService.obtenerCategorias());
    }
}
