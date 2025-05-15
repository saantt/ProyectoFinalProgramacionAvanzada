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
        Usuario usuario = usuarioRepository.findByEmail(loginRequestDTO.getEmail())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), usuario.getPassword())) {
            throw new Exception("Contraseña incorrecta");
        }

        // Asegúrate que usuario.getRol() devuelve "CIUDADANO" o "ADMINISTRADOR"
        // exactamente
        String token = jwtUtils.generarToken(usuario.getId().toString(), usuario.getEmail(), usuario.getRol().name());

        return new LoginResponseDTO(token);
    }
}