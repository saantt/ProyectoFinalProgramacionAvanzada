package co.edu.uniquindio.proyectofinal.proyecto.model;

import co.edu.uniquindio.proyectofinal.proyecto.model.common.Auditable;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("notificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notificacion extends Auditable {

    @Id
    private String id;

    private String mensaje;

    private String idUsuario;

}
