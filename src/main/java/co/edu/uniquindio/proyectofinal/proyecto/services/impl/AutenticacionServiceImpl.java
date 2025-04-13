package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.LoginRequestDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.LoginResponseDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.TokenDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyecto.repository.UsuarioRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.AutenticacionService;
import co.edu.uniquindio.proyectofinal.proyecto.util.JWTUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutenticacionServiceImpl implements AutenticacionService {

    private final UsuarioRepository usuarioRepository;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponseDTO login(@Valid LoginRequestDTO loginRequestDTO) throws Exception {
        // 1. Buscar usuario por email (usando el método correcto del repositorio)
        Usuario usuario = usuarioRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        // 2. Validar contraseña
        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), usuario.getPassword())) {
            throw new Exception("Contraseña incorrecta");
        }

        // 3. Generar token JWT con el rol
        String token = jwtUtils.generarToken(
                usuario.getEmail(), // ← Correo como subject
                Map.of("rol", usuario.getRol().name()));

        // 4. Retornar respuesta
        return new LoginResponseDTO(token);
    }
}