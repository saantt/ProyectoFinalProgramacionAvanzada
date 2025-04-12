package co.edu.uniquindio.proyectofinal.proyecto.dto.auth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record TokenDTO (
        String token,
        String refreshToken
){}