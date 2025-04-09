package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.UsuarioDTO;

import co.edu.uniquindio.proyectofinal.proyecto.exception.ResourceNotFoundException;
import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyecto.repository.UsuarioRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.UsuarioService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public String crearUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setRol(usuarioDTO.getRol());

        usuarioRepository.save(usuario);
        return "Usuario creado correctamente";
    }

    @Override
    public UsuarioDTO obtenerUsuario(String id) throws Exception {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getPassword(),
                usuario.getRol()
        );
    }

    @Override
    public String actualizarUsuario(String id, UsuarioDTO usuarioDTO) throws Exception {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setPassword(usuarioDTO.getPassword());

        usuarioRepository.save(usuario);
        return "Usuario actualizado correctamente";
    }

    @Override
    public String eliminarUsuario(String id) throws Exception {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
        return "Usuario eliminado correctamente";
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(
                usuario -> new UsuarioDTO(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getCorreo(),
                        usuario.getPassword(),
                        usuario.getRol()

                )
        ).collect(Collectors.toList());
    }

   
}
