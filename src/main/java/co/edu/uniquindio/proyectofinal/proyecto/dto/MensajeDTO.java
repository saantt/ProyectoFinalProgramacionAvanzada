package co.edu.uniquindio.proyectofinal.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MensajeDTO<T> {
    private boolean error;
    private T respuesta;
    private String mensaje; // Campo adicional para mensajes de error

    // Constructor simplificado (sin mensaje)
    public MensajeDTO(boolean error, T respuesta) {
        this.error = error;
        this.respuesta = respuesta;
    }
}
