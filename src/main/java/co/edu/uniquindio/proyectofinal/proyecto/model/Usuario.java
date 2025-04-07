package co.edu.uniquindio.proyectofinal.proyecto.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    private String id;

    private String nombre;
    private String email;
    private String telefono;
}
