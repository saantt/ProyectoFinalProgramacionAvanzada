package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.*;

import java.util.List;

public interface UsuarioService {
    String registrarUsuario(UsuarioRegistroDTO dto);
    String loginUsuario(UsuarioLoginDTO dto);
    UsuarioRespuestaDTO obtenerUsuario(String id);
    List<UsuarioDTO> listarUsuarios();
    void eliminarUsuario(String id);
    void cambiarEstado(CambioEstadoUsuarioDTO dto);
    String crearUsuario(UsuarioDTO usuarioDTO);

}
