package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.model.CodigoValidacion;

import java.util.Optional;

public interface CodigoValidacionService {
    CodigoValidacion guardar(CodigoValidacion codigo);

    CodigoValidacion obtenerPorCodigo(String codigo);

    Optional<CodigoValidacion> obtenerPorEmail(String email);

    CodigoValidacion generarNuevoCodigo(String email);

    boolean validarCodigo(String email, String codigo);
}