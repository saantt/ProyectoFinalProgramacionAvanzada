package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion.UbicacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Ubicacion;
import co.edu.uniquindio.proyectofinal.proyecto.repository.UbicacionRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.UbicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UbicacionServiceImpl implements UbicacionService {

    private final UbicacionRepository ubicacionRepository;

    @Autowired
    public UbicacionServiceImpl(UbicacionRepository ubicacionRepository) {
        this.ubicacionRepository = ubicacionRepository;
    }

    @Override
    public Ubicacion guardar(Ubicacion ubicacion) {
        return ubicacionRepository.save(ubicacion);
    }

    @Override
    public Ubicacion obtenerPorId(String id) {
        return ubicacionRepository.findById(id).orElse(null);
    }

    @Override
    public UbicacionDTO obtenerUbicacionPorReporte(String idReporte) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerUbicacionPorReporte'");
    }
}
