package co.edu.uniquindio.proyectofinal.proyecto.model;

import co.edu.uniquindio.proyectofinal.proyecto.model.enums.TipoNotificacion;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("notificaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Notificacion {

    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String titulo;
    private LocalDateTime fechaCreacion;
    private String tipoNotificacion;
    private boolean leida;
    private String reporteId;
    private String usuarioId;
    private String sector;
    private String mensaje;
    private String usuarioEmail;
    private boolean enviarEmail;

    public boolean isEnviarEmail() {
        return this.enviarEmail;
    }

    public void setEnviarEmail(boolean enviarEmail) {
        this.enviarEmail = enviarEmail;
    }
}
