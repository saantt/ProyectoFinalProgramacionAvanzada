package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.model.EstadoReporte;

import java.util.List;

 public interface EstadoReporteService {

    EstadoReporte guardar(EstadoReporte estadoReporte);

    List<EstadoReporte> obtenerTodos();

    Object obtenerEstados();
} 
/* public interface EstadoReporteService {
    List<String> obtenerEstados();
} */
