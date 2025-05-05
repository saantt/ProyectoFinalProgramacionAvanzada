package co.edu.uniquindio.proyectofinal.proyecto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uniquindio.proyectofinal.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.estado.CambiarEstadoDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.EditarReporteDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.ReporteCreacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.ReporteDTO;
import co.edu.uniquindio.proyectofinal.proyecto.services.ReporteService;
import co.edu.uniquindio.proyectofinal.proyecto.util.JWTUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService reporteServicio;
    // private JWTUtils jwtUtils;

    @PostMapping
    public ResponseEntity<MensajeDTO<String>> crear(@Valid @RequestBody ReporteCreacionDTO reporte) {
        try {
            reporteServicio.crearReporte(reporte);
            return ResponseEntity.ok(new MensajeDTO<>(false, "Reporte creado exitosamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MensajeDTO<>(true, "Error al crear reporte: " + e.getMessage()));
        }
    }

    // Editar un reporte existente
    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>> editar(@PathVariable String id, @Valid @RequestBody EditarReporteDTO dto)
            throws Exception {
        reporteServicio.actualizarReporte(id, dto);
        return ResponseEntity.ok(new MensajeDTO<>(false, "Reporte editado correctamente"));
    }

    // Eliminar un reporte
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable String id) throws Exception {
        reporteServicio.eliminarReporte(id);
        return ResponseEntity.ok("Reporte eliminado correctamente");
    }

    // Obtener un reporte específico
    @GetMapping("/{id}")
    public ResponseEntity<ReporteDTO> obtener(@PathVariable String id) throws Exception {
        ReporteDTO reporte = reporteServicio.obtener(id);
        return ResponseEntity.ok(reporte);
    }

    // // Listar reportes con filtros opcionales
    // @GetMapping
    // public ResponseEntity<List<ReporteDTO>> listar(
    // @RequestParam(required = false) String titulo,
    // @RequestParam(required = false) String categoria,
    // @RequestParam(required = false) String estado,
    // @RequestParam(defaultValue = "0") int pagina
    // ) {
    // List<ReporteDTO> reportes = reporteServicio.listar(titulo, categoria, estado,
    // pagina);
    // return ResponseEntity.ok(reportes);
    // }

    // Marcar un reporte como importante
    @PutMapping("/{id}/importante")
    public ResponseEntity<String> marcarImportante(@PathVariable String id) throws Exception {
        reporteServicio.marcarImportante(id);
        return ResponseEntity.ok("Reporte marcado como importante");
    }

    // Cambiar el estado de un reporte
    @PutMapping("/{id}/estado/{nuevoEstado}")
    public ResponseEntity<String> cambiarEstado(@PathVariable String id,
            @PathVariable CambiarEstadoDTO cambiarEstadoDTO) throws Exception {
        reporteServicio.cambiarEstadoReporte(id, cambiarEstadoDTO);
        return ResponseEntity.ok("Estado del reporte actualizado a: " + cambiarEstadoDTO);
    }
}