package co.edu.uniquindio.proyectofinal.proyecto.model;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class HistorialReporte {

    private EstadoReporte estado;
    private String observacion;
    private LocalDateTime fecha;
    private ObjectId clienteId;
}
