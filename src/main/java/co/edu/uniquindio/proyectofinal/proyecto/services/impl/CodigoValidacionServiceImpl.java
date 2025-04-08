package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import co.edu.uniquindio.proyectofinal.proyecto.model.CodigoValidacion;
import co.edu.uniquindio.proyectofinal.proyecto.repository.CodigoValidacionRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.CodigoValidacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CodigoValidacionServiceImpl implements CodigoValidacionService {

    @Autowired
    private CodigoValidacionRepository codigoValidacionRepository;

    @Override
    public CodigoValidacion guardar(CodigoValidacion codigo) {
        return codigoValidacionRepository.save(codigo);
    }

    @Override
    public CodigoValidacion obtenerPorCodigo(String codigo) {
        return codigoValidacionRepository.findById(codigo).orElse(null);
    }
}
