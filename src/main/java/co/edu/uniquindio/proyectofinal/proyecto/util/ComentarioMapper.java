package co.edu.uniquindio.proyectofinal.proyecto.util;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.uniquindio.proyectofinal.proyecto.dto.comentario.ComentarioCreacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.comentario.ComentarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Comentario;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {

    // Entrada: CrearComentarioDTO -> Comentario
    
    // Comentario toDocument(ComentarioCreacionDTO crearComentarioDTO); // Removed duplicate method

    // Salida: Comentario -> ComentarioDTO
    @Mapping(source = "usuarioId", target = "clienteId")
    ComentarioDTO toDTO(Comentario comentario);

    // Conversión personalizada
    default ObjectId map(String value) {
        return value != null ? new ObjectId(value) : null;
    }

    default String map(ObjectId value) {
        return value != null ? value.toHexString() : null;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fecha", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "reporteId", ignore = true)
    @Mapping(target = "usuarioId", source = "clienteId")
    Comentario toDocument(ComentarioCreacionDTO crearComentarioDTO);
}
