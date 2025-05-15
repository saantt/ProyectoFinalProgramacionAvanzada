package co.edu.uniquindio.proyectofinal.proyecto.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component

public class JWTUtils {

    public String generarToken(String id, String email, String rol) { // Cambia a String
        Instant now = Instant.now();

        return Jwts.builder()
                .claim("rol", rol)
                .claim("email", email)
                .subject(id)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(1L, ChronoUnit.HOURS)))
                .signWith(getKey())
                .compact();
    }

    public Jws<Claims> parseJwt(String jwtString)
            throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException {
        JwtParser jwtParser = Jwts.parser().verifyWith(getKey()).build();
        return jwtParser.parseSignedClaims(jwtString);
    }

    private SecretKey getKey() {
        String claveSecreta = "secretsecretsecretsecretsecretsecretsecretsecret";
        byte[] secretKeyBytes = claveSecreta.getBytes();
        return Keys.hmacShaKeyFor(secretKeyBytes);
    }

    public String obtenerRolDesdeToken(String token) throws Exception {
        Jws<Claims> claims = parseJwt(token); // Reutiliza tu método existente
        return claims.getBody().get("rol", String.class);
    }

}