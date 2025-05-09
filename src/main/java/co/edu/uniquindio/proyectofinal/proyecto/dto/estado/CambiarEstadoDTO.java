package co.edu.uniquindio.proyectofinal.proyecto.dto.estado;

import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoReporteEnum;
import jakarta.validation.constraints.NotNull;

public class CambiarEstadoDTO {
    @NotNull
    private EstadoReporteEnum nuevoEstado;

    // Getters y Setters
    public EstadoReporteEnum getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(EstadoReporteEnum nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }
}