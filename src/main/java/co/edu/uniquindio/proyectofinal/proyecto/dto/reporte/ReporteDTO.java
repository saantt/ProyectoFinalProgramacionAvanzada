
package co.edu.uniquindio.proyectofinal.proyecto.dto.reporte;

import co.edu.uniquindio.proyectofinal.proyecto.model.Ubicacion;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoReporteEnum;
import co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion.UbicacionDTO;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReporteDTO {
    public ReporteDTO(String id2, String titulo2, String descripcion2, String idUsuario2, String idCategoria2,
            Ubicacion ubicacion2, EstadoReporteEnum estado2, LocalDateTime fechaCreacion2) {
        //TODO Auto-generated constructor stub
    }
    private String id;
    private String titulo;
    private String descripcion;
    private String idUsuario;
    private String idCategoria;
    private UbicacionDTO ubicacion;
    private EstadoReporteEnum estado;
    private LocalDateTime fechaCreacion;
}
