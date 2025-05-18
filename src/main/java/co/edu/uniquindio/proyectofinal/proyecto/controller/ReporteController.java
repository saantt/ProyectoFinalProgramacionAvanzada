package co.edu.uniquindio.proyectofinal.proyecto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uniquindio.proyectofinal.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.estado.CambiarEstadoDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.EditarReporteDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.FiltroInformeDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.InformeGeneradoDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.ReporteCreacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.ReporteDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.ReporteInformeDTO;
import co.edu.uniquindio.proyectofinal.proyecto.services.ReporteService;
import co.edu.uniquindio.proyectofinal.proyecto.util.JWTUtils;
import org.springframework.http.ContentDisposition;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;

import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;

import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

    // Obtener reportes
    @GetMapping
    public ResponseEntity<MensajeDTO<List<ReporteDTO>>> listarTodos() {
        try {
            List<ReporteDTO> lista = reporteServicio.obtener();
            return ResponseEntity.ok(new MensajeDTO<>(false, lista));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MensajeDTO<>(true, null));
        }
    }

    // Marcar un reporte como importante
    @PutMapping("/{id}/importante")
    public ResponseEntity<String> marcarImportante(@PathVariable String id) throws Exception {
        reporteServicio.marcarImportante(id);
        return ResponseEntity.ok("Reporte marcado como importante");
    }

    // Cambiar el estado de un reporte
    @PutMapping("/{id}/estado")
    public ResponseEntity<String> cambiarEstado(
            @PathVariable String id,
            @Valid @RequestBody CambiarEstadoDTO cambiarEstadoDTO) throws Exception {
        reporteServicio.cambiarEstadoReporte(id, cambiarEstadoDTO);
        return ResponseEntity.ok("Estado del reporte actualizado a: " + cambiarEstadoDTO.getNuevoEstado());
    }

    @GetMapping("/informe")
    public ResponseEntity<MensajeDTO<InformeGeneradoDTO>> generarInforme(
            @RequestParam String categoriaId,
            @RequestParam String sector,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {

        LocalDateTime fechaInicioUTC = fechaInicio.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"))
                .toLocalDateTime();
        LocalDateTime fechaFinUTC = fechaFin.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"))
                .toLocalDateTime();

        FiltroInformeDTO filtro = new FiltroInformeDTO(categoriaId, sector, fechaInicioUTC, fechaFinUTC);

        InformeGeneradoDTO informe = reporteServicio.generarInforme(filtro);
        return ResponseEntity.ok(new MensajeDTO<>(false, informe));
    }

    @GetMapping(value = "/informe/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    @CrossOrigin(origins = { "http://localhost:4200", "http://localhost:8080" }) // Doble seguridad
    public ResponseEntity<byte[]> generarInformePDF(
            @RequestParam(required = false) String categoriaId,
            @RequestParam(required = false) String sector,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {

        LocalDateTime fechaInicioUTC = fechaInicio.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"))
                .toLocalDateTime();
        LocalDateTime fechaFinUTC = fechaFin.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC"))
                .toLocalDateTime();

        FiltroInformeDTO filtro = new FiltroInformeDTO(categoriaId, sector, fechaInicioUTC, fechaFinUTC);

        byte[] pdfBytes = reporteServicio.generarInformePDF(filtro);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(
                ContentDisposition.builder("attachment")
                        .filename("informe_reportes.pdf")
                        .build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

}