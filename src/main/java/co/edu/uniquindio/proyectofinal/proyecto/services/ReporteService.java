package co.edu.uniquindio.proyectofinal.proyecto.services;

import java.util.List;

import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.ReporteCreacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.ReporteDTO;

public interface ReporteService {
    String crearReporte(ReporteCreacionDTO dto);
    List<ReporteDTO> listarReportes();
    ReporteDTO obtenerReporte(String id);
    void eliminarReporte(String id);
}