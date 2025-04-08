package co.edu.uniquindio.proyectofinal.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MensajeDTO<T> {
    private boolean error;
    private T respuesta;
}
