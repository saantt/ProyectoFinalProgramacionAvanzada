package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.*;

import java.util.List;

public interface ReporteService {
    String crearReporte(ReporteCreacionDTO dto);
    ReporteRespuestaDTO obtenerReporte(String id);
    List<ReporteRespuestaDTO> listarReportes();
    void cambiarEstado(CambioEstadoReporteDTO dto);
}
