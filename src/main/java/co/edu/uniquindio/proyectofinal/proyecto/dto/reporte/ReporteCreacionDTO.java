package co.edu.uniquindio.proyectofinal.proyecto.dto.reporte;

import co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion.UbicacionDTO;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReporteCreacionDTO {
    private String titulo;
    private String descripcion;
    private String idUsuario;
    private String idCategoria;
    private UbicacionDTO ubicacion;
}
