package co.edu.uniquindio.proyectofinal.proyecto.util;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.CrearUsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.EditarUsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.UsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(target = "rol", constant = "CIUDADANO")
    @Mapping(target = "estado", constant = "INACTIVO")
    @Mapping(target = "fechaRegistro", expression = "java(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME))")
    Usuario toDocument(CrearUsuarioDTO dto); 

    UsuarioDTO toDTO(Usuario usuario);

    
    default String map(ObjectId value) {
        return value != null ? value.toHexString() : null;
    }


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "rol", ignore = true)
    @Mapping(target = "fechaRegistro", ignore = true)
    @Mapping(target = "codigoValidacion", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    void editarUsuarioDTO(EditarUsuarioDTO dto, @MappingTarget Usuario usuario);

}
