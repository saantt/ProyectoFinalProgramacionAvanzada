package co.edu.uniquindio.proyectofinal.proyecto.controller;

import co.edu.uniquindio.proyectofinal.proyecto.services.EstadoReporteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados-reporte")
@RequiredArgsConstructor
@Tag(name = "Estados de Reporte")
public class EstadoReporteController {

    private final EstadoReporteService estadoReporteService;

    @GetMapping
    public ResponseEntity<Object> listarEstados() {
        return ResponseEntity.ok(estadoReporteService.obtenerEstados());
    }
}
