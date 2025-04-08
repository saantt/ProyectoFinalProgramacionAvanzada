package co.edu.uniquindio.proyectofinal.proyecto.exception;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
