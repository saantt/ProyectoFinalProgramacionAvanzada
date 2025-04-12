package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import java.util.Map;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.LoginRequestDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.LoginResponseDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.TokenDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.UsuarioLoginDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyecto.repository.UsuarioRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.AutenticacionService;
import co.edu.uniquindio.proyectofinal.proyecto.services.EmailService;
import co.edu.uniquindio.proyectofinal.proyecto.util.JWTUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@Service
public class AutenticacionServiceImpl implements AutenticacionService {

    private final PasswordEncoder passwordEncoder;

    private final UsuarioRepository usuarioRepository;
    private final JWTUtils jwtUtils;
    private final EmailService emailService;

    public AutenticacionServiceImpl(UsuarioRepository usuarioRepository, JWTUtils jwtUtils, EmailService emailService,
            PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws Exception {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByemail(loginRequestDTO.getEmail());

        if (optionalUsuario.isEmpty()) {
            throw new Exception("El usuario no existe");
        }

        Usuario usuario = optionalUsuario.get();

        if (!passwordEncoder.matches(loginRequestDTO.getPassword(), usuario.getPassword())) {
            throw new Exception("Contraseña incorrecta");
        }

        String token = jwtUtils.generarToken(usuario.getId().toString(), crearClaims(usuario));
        return new LoginResponseDTO(token); // Usa LoginResponseDTO en lugar de LoginRequestDTO
    }

    private Map<String, Object> crearClaims(Usuario usuario) {
        return Map.of(
                "email", usuario.getEmail(),
                "nombre", usuario.getNombre(),
                "rol", "ROLE_" + usuario.getRol().name());
    }

}
