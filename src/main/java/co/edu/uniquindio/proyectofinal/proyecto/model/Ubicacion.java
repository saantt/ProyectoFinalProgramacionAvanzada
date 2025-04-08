package co.edu.uniquindio.proyectofinal.proyecto.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ubicacion {

    private String direccion;

    private double latitud;

    private double longitud;

}
