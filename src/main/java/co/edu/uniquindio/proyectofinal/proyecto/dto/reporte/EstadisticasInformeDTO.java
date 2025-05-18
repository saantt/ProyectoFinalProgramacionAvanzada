package co.edu.uniquindio.proyectofinal.proyecto.dto.reporte;

import java.util.Map;

public record EstadisticasInformeDTO(
        int totalReportes,
        int reportesImportantes,
        Map<String, Integer> reportesPorCategoria,
        Map<String, Integer> reportesPorEstado) {
}