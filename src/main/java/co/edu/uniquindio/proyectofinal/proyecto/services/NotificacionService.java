package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.model.Notificacion;

import java.util.List;
import java.util.Optional;

public interface NotificacionService {

    Notificacion guardar(Notificacion notificacion);

    Optional<Notificacion> obtenerPorUsuario(String idUsuario);
}
