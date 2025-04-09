package co.edu.uniquindio.proyectofinal.proyecto.model;

import co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion.UbicacionDTO;
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

    private String IdUsuario;

    private Ubicacion ubicacion;

    private String idCategoria;

    public void setUbicacion(UbicacionDTO ubicacion2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUbicacion'");
    }

    

}
