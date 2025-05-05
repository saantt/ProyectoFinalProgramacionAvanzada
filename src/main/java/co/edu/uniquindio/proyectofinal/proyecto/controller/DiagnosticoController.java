package co.edu.uniquindio.proyectofinal.proyecto.controller;

import co.edu.uniquindio.proyectofinal.proyecto.util.JWTUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.lang.Collections;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/diagnostico")
public class DiagnosticoController {

    private final JWTUtils jwtUtils;

    public DiagnosticoController(JWTUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @GetMapping("/inspect-token")
    public ResponseEntity<?> inspectToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                // Convertir Enumeration a List manualmente
                List<String> headerNames = new ArrayList<>();
                Enumeration<String> headers = request.getHeaderNames();
                while (headers.hasMoreElements()) {
                    headerNames.add(headers.nextElement());
                }

                return ResponseEntity.badRequest().body(Map.of(
                        "error", true,
                        "mensaje", "Cabecera Authorization faltante o mal formada",
                        "headersReceived", headerNames));
            }

            String token = authHeader.substring(7);
            Jws<Claims> jws = jwtUtils.parseJwt(token);

            // Convertir headers para la respuesta exitosa
            List<String> allHeaders = new ArrayList<>();
            Enumeration<String> headers = request.getHeaderNames();
            while (headers.hasMoreElements()) {
                allHeaders.add(headers.nextElement());
            }

            return ResponseEntity.ok(Map.of(
                    "subject", jws.getBody().getSubject(),
                    "rol", jws.getBody().get("rol"),
                    "expiration", jws.getBody().getExpiration(),
                    "isValid", true,
                    "headersInRequest", allHeaders));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of(
                    "error", true,
                    "mensaje", "Error procesando token: " + e.getMessage()));
        }
    }
}