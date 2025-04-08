package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import org.springframework.stereotype.Service;

import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.ReporteCreacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.ReporteDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Reporte;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoReporteEnum;
import co.edu.uniquindio.proyectofinal.proyecto.repository.ReporteRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.ReporteService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReporteServiceImpl implements ReporteService {

    private final ReporteRepository reporteRepository;

    public ReporteServiceImpl(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    @Override
    public String crearReporte(ReporteCreacionDTO dto) {
        Reporte reporte = new Reporte();
        reporte.setId(UUID.randomUUID().toString());
        reporte.setTitulo(dto.getTitulo());
        reporte.setDescripcion(dto.getDescripcion());
        reporte.setIdUsuario(dto.getIdUsuario());
        reporte.setIdCategoria(dto.getIdCategoria());
        reporte.setUbicacion(dto.getUbicacion());
        reporte.setEstado(EstadoReporteEnum.ENVIADO);
        reporte.setFechaCreacion(LocalDateTime.now());

        reporteRepository.save(reporte);
        return reporte.getId();
    }

    @Override
    public List<ReporteDTO> listarReportes() {
        return reporteRepository.findAll().stream().map(r ->
                new ReporteDTO(
                        r.getId(), r.getTitulo(), r.getDescripcion(),
                        r.getIdUsuario(), r.getIdCategoria(), r.getUbicacion(),
                        r.getEstado(), r.getFechaCreacion()
                )
        ).collect(Collectors.toList());
    }

    @Override
    public ReporteDTO obtenerReporte(String id) {
        Reporte r = reporteRepository.findById(id).orElseThrow(() -> new RuntimeException("Reporte no encontrado"));
        return new ReporteDTO(
                r.getId(), r.getTitulo(), r.getDescripcion(),
                r.getIdUsuario(), r.getIdCategoria(), r.getUbicacion(),
                r.getEstado(), r.getFechaCreacion()
        );
    }

    @Override
    public void eliminarReporte(String id) {
        if (!reporteRepository.existsById(id)) {
            throw new RuntimeException("No existe el reporte con ID " + id);
        }
        reporteRepository.deleteById(id);
    }
}