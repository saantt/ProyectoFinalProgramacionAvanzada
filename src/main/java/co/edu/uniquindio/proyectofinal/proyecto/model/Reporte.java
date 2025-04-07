package co.edu.uniquindio.proyectofinal.proyecto.model;

import co.edu.uniquindio.proyectofinal.proyecto.model.common.Auditable;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoReporteEnum;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("reportes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reporte extends Auditable {

    @Id
    private String id;

    private String titulo;

    private String descripcion;

    private EstadoReporteEnum estado;

    private String idUsuario;

    private Ubicacion ubicacion;

    private String idCategoria;

}
