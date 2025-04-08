package co.edu.uniquindio.proyectofinal.proyecto.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("codigos_validacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CodigoValidacion {

    @Id
    private String id;

    private String codigo;
    private String correo;

    private String idUsuario;

    private LocalDateTime fechaExpiracion;

}
