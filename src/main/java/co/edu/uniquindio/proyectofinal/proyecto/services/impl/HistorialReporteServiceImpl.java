package co.edu.uniquindio.proyectofinal.proyecto.services.impl;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.HistorialReporteCreacionDTO;    

import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.HistorialReporteDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.HistorialReporte;
import co.edu.uniquindio.proyectofinal.proyecto.repository.HistorialReporteRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.HistorialReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistorialReporteServiceImpl implements HistorialReporteService {

    private final HistorialReporteRepository historialReporteRepository;

    @Override
    public void crearHistorial(HistorialReporteCreacionDTO dto) {
        HistorialReporte historial = new HistorialReporte();
        historial.setIdReporte(dto.getIdReporte());
        historial.setObservacion(dto.getDescripcion());
        historial.setFechaCambio(LocalDateTime.now());

        historialReporteRepository.save(historial);
    }

    @Override
    public List<HistorialReporteDTO> listarHistorialesPorReporte(String idReporte) {
        return historialReporteRepository.findByIdReporte(idReporte)
                .stream()
                .map(h -> new HistorialReporteDTO(
                        h.getId(),
                        h.getIdReporte(),
                        h.getObservacion(),
                        h.getFechaCambio()
                ))
                .collect(Collectors.toList());
    }
}
