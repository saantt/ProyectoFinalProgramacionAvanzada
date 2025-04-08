package co.edu.uniquindio.proyectofinal.proyecto.services;

import java.util.List;

import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.HistorialReporteCreacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.HistorialReporteDTO;

public interface HistorialReporteService {
    void crearHistorial(HistorialReporteCreacionDTO dto);

    List<HistorialReporteDTO> listarHistorialesPorReporte(String idReporte);
}
