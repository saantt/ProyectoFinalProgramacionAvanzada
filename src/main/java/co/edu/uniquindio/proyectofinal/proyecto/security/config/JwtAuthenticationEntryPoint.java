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
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        MensajeDTO<String> dto = new MensajeDTO<>(true, "No tienes permisos para acceder a este recurso");

        response.setContentType("application/json");
        response.setStatus(403);
        response.getWriter().write(new ObjectMapper().writeValueAsString(dto));
        response.getWriter().flush();
        response.getWriter().close();

    }

}
