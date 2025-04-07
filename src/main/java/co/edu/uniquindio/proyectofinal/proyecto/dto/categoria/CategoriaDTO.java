package co.edu.uniquindio.proyectofinal.proyecto.dto.categoria;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoriaDTO {
    private String id;
    private String nombre;
    private String descripcion;
}
