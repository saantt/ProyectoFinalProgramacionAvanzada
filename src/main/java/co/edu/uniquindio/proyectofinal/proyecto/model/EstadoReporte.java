package co.edu.uniquindio.proyectofinal.proyecto.model;

import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoReporteEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("estados_reporte")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoReporte {

    @Id
    private String id;

    private String idReporte;
    private String reporteId;
    private String nombre;
    private EstadoReporteEnum estado;
    private String observacion;

    // ✅ Estados estáticos válidos
    public static final EstadoReporte PENDIENTE = EstadoReporte.builder()
            .nombre("Pendiente")
            .estado(EstadoReporteEnum.PENDIENTE)
            .build();

    public static final EstadoReporte ELIMINADO = EstadoReporte.builder()
            .nombre("Eliminado")
            .estado(EstadoReporteEnum.ELIMINADO) // O el enum que represente "eliminado"
            .build();
}
