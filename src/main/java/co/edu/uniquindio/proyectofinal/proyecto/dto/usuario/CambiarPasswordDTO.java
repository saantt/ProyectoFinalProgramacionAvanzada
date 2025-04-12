package co.edu.uniquindio.proyectofinal.proyecto.dto.usuario;

public record CambiarPasswordDTO(

        String email,
        String codigoVerificacion,
        String passwordNueva
) {
}