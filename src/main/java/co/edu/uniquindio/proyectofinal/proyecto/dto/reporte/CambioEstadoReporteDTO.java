package co.edu.uniquindio.proyectofinal.proyecto.dto.reporte;

import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoReporteEnum;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CambioEstadoReporteDTO {
    private String idReporte;
    private EstadoReporteEnum nuevoEstado;
    private String observacion;
}
