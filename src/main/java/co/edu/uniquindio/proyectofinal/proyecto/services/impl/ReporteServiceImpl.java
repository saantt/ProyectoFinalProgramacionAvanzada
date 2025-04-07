package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.CambioEstadoReporteDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.ReporteCreacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.ReporteRespuestaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Reporte;
import co.edu.uniquindio.proyectofinal.proyecto.repository.ReporteRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.ReporteService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    @Override
    public String crearReporte(ReporteCreacionDTO dto) {
        Reporte reporte = new Reporte();
        reporte.setTitulo(dto.getTitulo());
        reporte.setDescripcion(dto.getDescripcion());
        // otros campos...
        return reporteRepository.save(reporte).getId();
    }

    @Override
    public ReporteRespuestaDTO obtenerReporte(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerReporte'");
    }

    @Override
    public List<ReporteRespuestaDTO> listarReportes() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarReportes'");
    }

    @Override
    public void cambiarEstado(CambioEstadoReporteDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cambiarEstado'");
    }
}
