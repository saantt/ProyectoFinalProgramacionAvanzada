package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.LoginRequestDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.LoginResponseDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyecto.repository.UsuarioRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.AutenticacionService;
import co.edu.uniquindio.proyectofinal.proyecto.services.EmailService;
import co.edu.uniquindio.proyectofinal.proyecto.util.JWTUtils;

@Service
public class AutenticacionServiceImpl implements AutenticacionService {

    private final UsuarioRepository usuarioRepository;
    private final JWTUtils jwtUtil;
    private final EmailService emailService;

    public AutenticacionServiceImpl(UsuarioRepository usuarioRepository, JWTUtils jwtUtil, EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO dto) throws Exception {
        Usuario usuario = usuarioRepository.findByCorreo(dto.getCorreo())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        if (!new BCryptPasswordEncoder().matches(dto.getPassword(), usuario.getPassword())) {
            throw new Exception("Contraseña incorrecta");
        }

        // String token = jwtUtil.generateToken(usuario.getCorreo());
        String token = jwtUtil.generateToken(usuario.getCorreo(), crearClaims(usuario));

        emailService.enviarCorreo(usuario.getCorreo(), "Tu token de autenticación", "Token: " + token);

        return new LoginResponseDTO(token);
    }

    private Map<String, String> crearClaims(Usuario usuario) {
        return Map.of(
                "email", usuario.getCorreo(),
                "nombre", usuario.getNombre(),
                "rol", "ROLE_" + usuario.getRol().name());
    }
}
