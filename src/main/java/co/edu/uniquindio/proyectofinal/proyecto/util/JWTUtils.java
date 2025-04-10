package co.edu.uniquindio.proyectofinal.proyecto.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import co.edu.uniquindio.proyectofinal.proyecto.model.enums.Rol;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

import java.util.Date;

@Component
public class JWTUtils {

    public String generateToken(String id, Map<String, String> claims) {
        Rol rol = Rol.valueOf(claims.get("rol"));   

        Instant now = Instant.now();

        return Jwts.builder()
                .claim("authorities", "ROLE_" + rol.name()) // ¡Formato clave!
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

   
    

}
