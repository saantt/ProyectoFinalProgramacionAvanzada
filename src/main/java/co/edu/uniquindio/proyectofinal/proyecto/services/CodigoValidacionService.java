package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.model.CodigoValidacion;

public interface CodigoValidacionService {

    CodigoValidacion guardar(CodigoValidacion codigo);

    CodigoValidacion obtenerPorCodigo(String codigo);
}
