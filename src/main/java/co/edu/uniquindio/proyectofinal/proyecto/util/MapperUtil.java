package co.edu.uniquindio.proyectofinal.proyecto.util;

import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.UsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;

public class MapperUtil {

    public static UsuarioDTO mapearUsuarioADTO(Usuario usuario) {
        return new UsuarioDTO(
            usuario.getId(),
            usuario.getNombre(),
            usuario.getCorreo(),
            usuario.getPassword(),
            usuario.getRol()
        );
    }

    public static Usuario mapearDTOAUsuario(UsuarioDTO dto) {
        Usuario usuario = new Usuario(null, null, null, null);
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(dto.getPassword());
        usuario.setRol(dto.getRol());
        return usuario;
    }
}
