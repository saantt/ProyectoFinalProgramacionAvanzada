package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.CambioEstadoUsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.UsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.UsuarioLoginDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.UsuarioRegistroDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.UsuarioRespuestaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyecto.repository.UsuarioRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public String crearUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario(
            usuarioDTO.getNombre(),
            usuarioDTO.getEmail(),
            usuarioDTO.getPassword(),
            usuarioDTO.getRol()
        );

        return usuarioRepository.save(usuario).getId();
    }

    @Override
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll().stream()
            .map(u -> new UsuarioDTO(
                u.getId(),
                u.getNombre(),
                u.getEmail(),
                u.getPassword(),
                u.getRol()
            ))
            .collect(Collectors.toList());
    }

    @Override
    public void cambiarEstado(CambioEstadoUsuarioDTO cambioEstadoUsuarioDTO) {
        // Aquí puedes implementar la lógica de cambio de estado si tu modelo Usuario tiene un campo "estado"
        // Por ejemplo:
        // Usuario usuario = usuarioRepository.findById(cambioEstadoUsuarioDTO.getId()).orElseThrow(...);
        // usuario.setEstado(cambioEstadoUsuarioDTO.getNuevoEstado());
        // usuarioRepository.save(usuario);
    }

    @Override
    public String registrarUsuario(UsuarioRegistroDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registrarUsuario'");
    }

    @Override
    public String loginUsuario(UsuarioLoginDTO dto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loginUsuario'");
    }

    @Override
    public UsuarioRespuestaDTO obtenerUsuario(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerUsuario'");
    }

    @Override
    public void eliminarUsuario(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarUsuario'");
    }

   
}
