package co.edu.uniquindio.proyectofinal.proyecto.services;


import co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion.UbicacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Ubicacion;

public interface UbicacionService {

    Ubicacion guardar(Ubicacion ubicacion);

    Ubicacion obtenerPorId(String id);

    UbicacionDTO obtenerUbicacionPorReporte(String idReporte); // <-- ESTE FALTABA
}
