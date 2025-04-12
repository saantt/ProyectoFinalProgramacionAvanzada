package co.edu.uniquindio.proyectofinal.proyecto.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Ubicacion {
    private double latitud;
    private double longitud;
}