package co.edu.uniquindio.proyectofinal.proyecto.services.impl;


import co.edu.uniquindio.proyectofinal.proyecto.services.EstadoReporteService;
import co.edu.uniquindio.proyectofinal.proyecto.model.EstadoReporte;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.EstadoReporteDTO;
import co.edu.uniquindio.proyectofinal.proyecto.repository.EstadoReporteRepository; 
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EstadoReporteServiceImpl implements EstadoReporteService {

    private final EstadoReporteRepository estadoReporteRepository;

    @Override
    public List<EstadoReporteDTO> obtenerEstados() {
        List<EstadoReporte> estados = estadoReporteRepository.findAll();

        return estados.stream()
                .map(e -> new EstadoReporteDTO(e.getId(), e.getNombre()))
                .collect(Collectors.toList());
    }

    @Override
    public EstadoReporte guardar(EstadoReporte estadoReporte) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'guardar'");
    }

    @Override
    public List<EstadoReporte> obtenerTodos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerTodos'");
    }

    
}
