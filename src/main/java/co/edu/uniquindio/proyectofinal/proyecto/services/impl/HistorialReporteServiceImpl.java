package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import co.edu.uniquindio.proyectofinal.proyecto.model.HistorialReporte;
import co.edu.uniquindio.proyectofinal.proyecto.repository.HistorialReporteRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.HistorialReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialReporteServiceImpl implements HistorialReporteService {

    private final HistorialReporteRepository historialReporteRepository;

    @Autowired
    public HistorialReporteServiceImpl(HistorialReporteRepository historialReporteRepository) {
        this.historialReporteRepository = historialReporteRepository;
    }

    @Override
    public HistorialReporte guardar(HistorialReporte historial) {
        return historialReporteRepository.save(historial);
    }

    @Override
    public List<HistorialReporte> obtenerPorIdReporte(String idReporte) {
        return historialReporteRepository.findByIdReporte(idReporte);
    }
}
