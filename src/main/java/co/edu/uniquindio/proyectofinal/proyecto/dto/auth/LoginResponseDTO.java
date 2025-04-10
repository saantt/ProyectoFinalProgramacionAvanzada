package co.edu.uniquindio.proyectofinal.proyecto.dto.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String mensaje;

    // Constructor adicional para solo el token
    public LoginResponseDTO(String token) {
        this.token = token;
        this.mensaje = "Autenticación exitosa"; // Mensaje por defecto
    }
}
