package co.edu.uniquindio.proyectofinal.proyecto.util;

import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.UsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;

public class MapperUtil {

    public static UsuarioDTO mapearUsuarioADTO(Usuario usuario) {
        return new UsuarioDTO(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getEmail(),
            usuario.getPassword(),
            usuario.getRol()
        );
    }

    public static Usuario mapearDTOAUsuario(UsuarioDTO dto) {
        Usuario usuario = new Usuario(null, null, null, null);
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());
        return usuario;
    }
}
