package co.edu.uniquindio.proyectofinal.proyecto.security.config;

import co.edu.uniquindio.proyectofinal.proyecto.dto.MensajeDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;
import java.io.IOException;

@Component

public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {

        // Devuelve 401 Unauthorized
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autorizado: Token inválido o inexistente");
    }

}
