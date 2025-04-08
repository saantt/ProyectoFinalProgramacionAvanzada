package co.edu.uniquindio.proyectofinal.proyecto.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("historial_reportes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialReporte {

    @Id
    private String id;

    private String idReporte;
    private String reporteId;

    private String estadoAnterior;

    private String estadoNuevo;

    private LocalDateTime fechaCambio;

    private String observacion;

}
