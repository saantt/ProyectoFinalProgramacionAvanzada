package co.edu.uniquindio.proyectofinal.proyecto.controller;

import co.edu.uniquindio.proyectofinal.proyecto.dto.*;
import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.LoginRequestDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.LoginResponseDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.TokenDTO;
import co.edu.uniquindio.proyectofinal.proyecto.services.AutenticacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    private final AutenticacionService autenticacionService;

    public LoginController(AutenticacionService autenticacionService) {
        this.autenticacionService = autenticacionService;
    }

    @PostMapping
    public ResponseEntity<MensajeDTO<LoginResponseDTO>> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            LoginResponseDTO respuesta = autenticacionService.login(loginRequestDTO);
            return ResponseEntity.ok(new MensajeDTO<>(false, respuesta));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(new MensajeDTO<>(true, null, e.getMessage()));
        }
    }
}
