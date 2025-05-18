package co.edu.uniquindio.proyectofinal.proyecto.dto.reporte;

import java.time.LocalDateTime;
import co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion.UbicacionDTO;

public record ReporteInformeDTO(
                String titulo,
                String descripcion,
                LocalDateTime fecha,
                UbicacionDTO ubicacion,
                String nombreCategoria, // Cambiado para almacenar el nombre
                boolean importante,
                String estado) {
}