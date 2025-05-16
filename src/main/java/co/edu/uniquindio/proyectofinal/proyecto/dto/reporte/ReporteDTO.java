
package co.edu.uniquindio.proyectofinal.proyecto.dto.reporte;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion.UbicacionDTO;

public record ReporteDTO(
        String id,
        String titulo,
        String descripcion,
        String estado,
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
