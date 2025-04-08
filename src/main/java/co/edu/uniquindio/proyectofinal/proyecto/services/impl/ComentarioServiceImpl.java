package co.edu.uniquindio.proyectofinal.proyecto.services.impl; 

import co.edu.uniquindio.proyectofinal.proyecto.dto.comentario.ComentarioCreacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.comentario.ComentarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Comentario;
import co.edu.uniquindio.proyectofinal.proyecto.repository.ComentarioRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.ComentarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;

    @Override
    public void crearComentario(ComentarioCreacionDTO dto) {
        Comentario comentario = new Comentario();
        comentario.setContenido(dto.getContenido());
        comentario.setIdUsuario(dto.getIdUsuario());
        comentario.setIdReporte(dto.getIdReporte());
        comentario.setFechaCreacion(LocalDateTime.now());

        comentarioRepository.save(comentario);
    }

    @Override
    public List<ComentarioDTO> listarComentariosPorReporte(String idReporte) {
        return comentarioRepository.findById(idReporte)
                .stream()
                .map(c -> new ComentarioDTO(
                        c.getId(),
                        c.getContenido(),
                        c.getIdUsuario(),
                        c.getIdReporte(),
                        c.getFechaCreacion()
                ))
                .collect(Collectors.toList());
    }
}
