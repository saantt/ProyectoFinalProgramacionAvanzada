package co.edu.uniquindio.proyectofinal.proyecto.dto.comentario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


public record ComentarioDTO(
         String id,
         String mensaje,
         LocalDateTime fecha,
         String clienteId,
         String nombreCliente,
         String reporteId
){}
