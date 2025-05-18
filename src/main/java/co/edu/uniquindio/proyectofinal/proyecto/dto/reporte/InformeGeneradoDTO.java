package co.edu.uniquindio.proyectofinal.proyecto.dto.reporte;

import java.time.LocalDateTime;
import java.util.List;

public record InformeGeneradoDTO(
        String tituloInforme,
        LocalDateTime fechaGeneracion,
        String periodo,
        int totalReportes,
        List<ReporteInformeDTO> reportes,
        EstadisticasInformeDTO estadisticas) {
}