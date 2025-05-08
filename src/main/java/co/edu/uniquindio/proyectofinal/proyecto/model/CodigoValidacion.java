package co.edu.uniquindio.proyectofinal.proyecto.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "codigos_validacion") // Define la colección en MongoDB
@Data // Genera getters, setters, equals, hashCode y toString
@Builder // Patrón builder para construcción fácil
@NoArgsConstructor // Constructor sin argumentos (necesario para Spring Data)
@AllArgsConstructor // Constructor con todos los argumentos
public class CodigoValidacion {

    @Id // Marca este campo como identificador único
    private String id; // MongoDB usa String para IDs por defecto

    private String codigo; // Campo que usaremos para buscar (asegúrate que existe)
    private LocalDateTime fechaCreacion;
    private String email;

    // Si necesitas lógica adicional, puedes agregar métodos como:
    public boolean estaExpirado() {
        return LocalDateTime.now().isAfter(fechaCreacion.plusMinutes(15));
    }

    public CodigoValidacion(String codigo, LocalDateTime fechaCreacion) {
        this.codigo = codigo;
        this.fechaCreacion = fechaCreacion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}