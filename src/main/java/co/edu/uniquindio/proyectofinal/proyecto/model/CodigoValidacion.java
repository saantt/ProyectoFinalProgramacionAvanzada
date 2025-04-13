package co.edu.uniquindio.proyectofinal.proyecto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class CodigoValidacion {
    private String codigo;
    private LocalDateTime fechaCreacion;
    private String email;

    public CodigoValidacion(String codigo, LocalDateTime fechaCreacion) {
        this.codigo = codigo;
        this.fechaCreacion = fechaCreacion;
    }
}