package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import co.edu.uniquindio.proyectofinal.proyecto.model.EstadoReporte;
import co.edu.uniquindio.proyectofinal.proyecto.repository.EstadoReporteRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.EstadoReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoReporteServiceImpl implements EstadoReporteService {

    private final EstadoReporteRepository estadoReporteRepository;

    @Autowired
    public EstadoReporteServiceImpl(EstadoReporteRepository estadoReporteRepository) {
        this.estadoReporteRepository = estadoReporteRepository;
    }

    @Override
    public EstadoReporte guardar(EstadoReporte estadoReporte) {
        return estadoReporteRepository.save(estadoReporte);
    }

    @Override
    public List<EstadoReporte> obtenerTodos() {
        return estadoReporteRepository.findAll();
    }
}
