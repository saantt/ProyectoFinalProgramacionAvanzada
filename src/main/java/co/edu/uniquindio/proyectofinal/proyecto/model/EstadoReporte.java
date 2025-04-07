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

    private EstadoReporteEnum estado;

    private String observacion;

}
