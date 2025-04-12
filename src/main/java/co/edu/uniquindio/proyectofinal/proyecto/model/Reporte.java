package co.edu.uniquindio.proyectofinal.proyecto.model;




import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("reportes")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Reporte {

    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;
    
    private String titulo;
    private ObjectId categoriaId;
    private String descripcion;
    private Ubicacion ubicacion;
    private List<String> fotos;
    private ObjectId clienteId; // Referencia al usuario que creó el reporte
    private LocalDateTime fecha;
    private List<HistorialReporte> historial;
    private EstadoReporte estadoActual;
    private int contadorImportante;


    @Builder
    public Reporte(String titulo, ObjectId categoriaid, String descripcion, Ubicacion ubicacion, List<String> fotos, ObjectId clienteId, LocalDateTime fecha, List<HistorialReporte> historial, EstadoReporte estadoActual, int contadorImportante) {
        this.titulo = titulo;
        this.categoriaId = categoriaid;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.fotos = fotos;
        this.clienteId = clienteId;
        this.fecha = fecha;
        this.historial = historial;
        this.estadoActual = estadoActual;
        this.contadorImportante = contadorImportante;
    }
}