
package co.edu.uniquindio.proyectofinal.proyecto.dto.reporte;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion.UbicacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.EstadoReporte;

public record ReporteDTO(
                String id,
                String titulo,
                String descripcion,
                EstadoReporte estadoActual,
                LocalDateTime fecha,
                boolean importante,
                String clienteId,
                String nombreCliente,
                UbicacionDTO ubicacion,
                String categoriaId,
                String nombreCategoria,
                List<String> fotos,
                Integer contadorImportante) {
}
