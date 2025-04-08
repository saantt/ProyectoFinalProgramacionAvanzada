package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import co.edu.uniquindio.proyectofinal.proyecto.model.Comentario;
import co.edu.uniquindio.proyectofinal.proyecto.repository.ComentarioRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.ComentarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;

    @Override
    public Comentario guardar(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    @Override
    public Optional<Comentario> obtenerComentariosPorReporte(String idReporte) {
        return comentarioRepository.findById(idReporte);
    }
}
