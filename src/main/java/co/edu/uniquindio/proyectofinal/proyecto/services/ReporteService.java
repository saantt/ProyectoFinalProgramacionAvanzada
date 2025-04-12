package co.edu.uniquindio.proyectofinal.proyecto.services;

import java.util.List;

import co.edu.uniquindio.proyectofinal.proyecto.dto.estado.CambiarEstadoDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.estado.HistorialEStadoDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.EditarReporteDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.InfoReporteDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.ReporteCreacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.ReporteDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.EstadoReporte;
import co.edu.uniquindio.proyectofinal.proyecto.model.Ubicacion;
import jakarta.validation.Valid;

public interface ReporteService {
    void crearReporte(@Valid ReporteCreacionDTO reporte) throws Exception;
    void eliminarReporte(String id) throws Exception;
    void actualizarReporte(String id, EditarReporteDTO reporte) throws Exception;
    ReporteDTO obtener(String id) throws Exception;
    void marcarImportante(String id) throws Exception;
    void cambiarEstadoReporte(String id, CambiarEstadoDTO estado) throws Exception;
    InfoReporteDTO obtenerReporte(String id) throws Exception;
    List<InfoReporteDTO> obtenerReportes(String categoria, EstadoReporte estadoReporte, int pagina) throws Exception;
    List<InfoReporteDTO> obtenerReportesUsuario(String idusuario, int pagina) throws Exception;
    List<InfoReporteDTO> obtenerReportes(Ubicacion ubicacion) throws Exception;
    List<HistorialEStadoDTO> listarHistorialEstados(String id) throws Exception;
}