package co.edu.uniquindio.proyectofinal.proyecto.dto.reporte;

import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoReporteEnum;
import co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion.UbicacionDTO;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReporteDTO {
    private String id;
    private String titulo;
    private String descripcion;
    private String idUsuario;
    private String idCategoria;
    private UbicacionDTO ubicacion;
    private EstadoReporteEnum estado;
    private LocalDateTime fechaCreacion;
}
