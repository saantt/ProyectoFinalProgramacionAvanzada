package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyecto.repository.UsuarioRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.ValidacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValidacionServiceImpl implements ValidacionService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public boolean validarCodigo(String codigo, String idUsuario) {

        // Buscar al usuario en la base de datos por su ID
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(idUsuario);

        if (usuarioOpt.isEmpty()) {
            return false;
        }

        Usuario usuario = usuarioOpt.get();

        // Comparar el código recibido con el que tiene guardado el usuario
        if (usuario.getCodigoVerificacion() != null && usuario.getCodigoVerificacion().equals(codigo)) {

            // (Opcional) limpiar o invalidar el código para que no se use otra vez
            usuario.setCodigoVerificacion(null);
            usuarioRepository.save(usuario);

            return true;
        }

        return false;
    }
}
