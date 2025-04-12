package co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record UbicacionDTO (
            @NotNull(message = "La latitud es obligatoria")
            Double latitud,

            @NotNull(message = "La longitud es obligatoria")
            Double longitud
){}
