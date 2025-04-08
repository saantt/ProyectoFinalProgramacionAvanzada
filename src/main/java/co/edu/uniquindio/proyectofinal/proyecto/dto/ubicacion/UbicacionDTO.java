package co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UbicacionDTO {
    private double latitud;
    private double longitud;
    private String direccion;
    private String ciudad;
}
