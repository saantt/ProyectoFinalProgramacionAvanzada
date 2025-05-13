package co.edu.uniquindio.proyectofinal.proyecto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uniquindio.proyectofinal.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.LoginDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.TokenDTO;
import co.edu.uniquindio.proyectofinal.proyecto.services.CuentaServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AutenticacionControlador {

    private final CuentaServicio cuentaServicio;

    @PostMapping("/iniciar-sesion")
    public ResponseEntity<MensajeDTO<?>> login(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            TokenDTO token = cuentaServicio.iniciarSesion(loginDTO);
            return ResponseEntity.ok().body(new MensajeDTO<>(false, token));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new MensajeDTO<>(true, e.getMessage()));
        }
    }
}
