package co.edu.uniquindio.proyectofinal.proyecto.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.HistorialReporteCreacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.HistorialReporteDTO;
import co.edu.uniquindio.proyectofinal.proyecto.services.HistorialReporteService;

import java.util.List;

@RestController
@RequestMapping("/api/historiales")
@RequiredArgsConstructor
public class HistorialReporteController {

    private final HistorialReporteService historialReporteService;

    @PostMapping
    public ResponseEntity<Void> crearHistorial(@RequestBody HistorialReporteCreacionDTO dto) {
        historialReporteService.crearHistorial(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{idReporte}")
    public ResponseEntity<List<HistorialReporteDTO>> listarHistoriales(@PathVariable String idReporte) {
        return ResponseEntity.ok(historialReporteService.listarHistorialesPorReporte(idReporte));
    }
}
