package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyecto.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;

    public UsuarioService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    public Usuario crearUsuario() {
        Usuario nuevo = Usuario.builder()
                .nombre("Santiago Gomez")
                .email("Santiago@gmail.com")
                .telefono("123456789")
                .build();

        return usuarioRepo.save(nuevo);
    }
}
