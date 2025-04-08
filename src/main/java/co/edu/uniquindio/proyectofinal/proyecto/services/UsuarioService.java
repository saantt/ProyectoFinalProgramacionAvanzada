package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.*;

import java.util.List;

public interface UsuarioService {

    String crearUsuario(UsuarioDTO usuarioDTO) throws Exception;

    UsuarioDTO obtenerUsuario(String id) throws Exception;

    String actualizarUsuario(String id, UsuarioDTO usuarioDTO) throws Exception;

    String eliminarUsuario(String id) throws Exception;

    List<UsuarioDTO> listarUsuarios();
}
