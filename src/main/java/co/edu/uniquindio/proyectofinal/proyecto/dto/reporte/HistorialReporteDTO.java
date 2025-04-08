package co.edu.uniquindio.proyectofinal.proyecto.dto.reporte;
import co.edu.uniquindio.proyectofinal.proyecto.model.HistorialReporte;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HistorialReporteDTO {
    private String id;
    private String idReporte;
    private String descripcion;
    private LocalDateTime fecha;
}
