package co.edu.uniquindio.proyectofinal.proyecto.util;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import co.edu.uniquindio.proyectofinal.proyecto.dto.categoria.CategoriaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Categoria;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    Categoria toDocument(CategoriaDTO dto);

    @Mapping(target = "id", source = "id", qualifiedByName = "objectIdToString")
    CategoriaDTO toDTO(Categoria categoria);

    @Named("objectIdToString")
    public static String mapObjectIdToString(ObjectId id) {
        return id != null ? id.toHexString() : null;
    }
}
