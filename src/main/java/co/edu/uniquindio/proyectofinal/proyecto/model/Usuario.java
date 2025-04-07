package co.edu.uniquindio.proyectofinal.proyecto.model;

import co.edu.uniquindio.proyectofinal.proyecto.model.common.Auditable;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoUsuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.Rol;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario extends Auditable {

    @Id
    private String id;

    private String nombre;

    private String correo;

    private String password;

    private EstadoUsuario estado;

    private Rol rol;

    private List<String> reportes;

}
