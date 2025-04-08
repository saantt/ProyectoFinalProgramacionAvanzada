package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.model.HistorialReporte;

import java.util.List;

public interface HistorialReporteService {

    HistorialReporte guardar(HistorialReporte historial);

    List<HistorialReporte> obtenerPorIdReporte(String idReporte);
}
