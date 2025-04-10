package co.edu.uniquindio.proyectofinal.proyecto.security;

import co.edu.uniquindio.proyectofinal.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.Rol;
import co.edu.uniquindio.proyectofinal.proyecto.util.JWTUtils;
import io.jsonwebtoken.*;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtils jwtUtils;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/api/login") || path.startsWith("/swagger-ui");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        // Configuración de cabeceras para CORS
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, Content-Type, Authorization");

        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            try {
                String token = getToken(request);

                if (token != null) {
                    Jws<Claims> jws = jwtUtils.parseJwt(token);
                    Claims claims = jws.getPayload();

                    // 1. Extraer el rol del token (debe estar como "ROLE_ROL")
                    String rolStr = claims.get("rol", String.class);
                    Rol rol = Rol.valueOf(rolStr.replace("ROLE_", ""));

                    // 2. Crear autenticación para Spring Security
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            claims.getSubject(),
                            null,
                            Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol.name())));

                    SecurityContextHolder.getContext().setAuthentication(auth);

                    // 3. Validar acceso según la ruta
                    String requestURI = request.getRequestURI();
                    boolean accesoPermitido = true;

                    if (requestURI.startsWith("/api/admin") && rol != Rol.ADMINISTRADOR) {
                        accesoPermitido = false;
                    } else if (requestURI.startsWith("/api/cliente") && rol != Rol.CIUDADANO) {
                        accesoPermitido = false;
                    }

                    if (!accesoPermitido) {
                        crearRespuestaError("No tiene permisos para acceder a este recurso",
                                HttpServletResponse.SC_FORBIDDEN, response);
                        return;
                    }
                } else {
                    crearRespuestaError("Token no proporcionado", HttpServletResponse.SC_UNAUTHORIZED, response);
                    return;
                }

                filterChain.doFilter(request, response);

            } catch (ExpiredJwtException e) {
                crearRespuestaError("El token está vencido", HttpServletResponse.SC_UNAUTHORIZED, response);
            } catch (MalformedJwtException | SignatureException e) {
                crearRespuestaError("Token inválido", HttpServletResponse.SC_FORBIDDEN, response);
            } catch (Exception e) {
                crearRespuestaError("Error de autenticación: " + e.getMessage(),
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response);
            }
        }
    }

    private String getToken(HttpServletRequest req) {
        String header = req.getHeader("Authorization");
        return header != null && header.startsWith("Bearer ") ? header.replace("Bearer ", "") : null;
    }

    private void crearRespuestaError(String mensaje, int codigoError, HttpServletResponse response) throws IOException {
        MensajeDTO<String> dto = new MensajeDTO<>(true, mensaje);

        response.setContentType("application/json");
        response.setStatus(codigoError);
        response.getWriter().write(new ObjectMapper().writeValueAsString(dto));
        response.getWriter().flush();
        response.getWriter().close();
    }
}