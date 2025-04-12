package co.edu.uniquindio.proyectofinal.proyecto.util;

import org.mapstruct.Mapper;

import co.edu.uniquindio.proyectofinal.proyecto.dto.categoria.CategoriaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Categoria;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    Categoria toDocument(CategoriaDTO dto);
    // Para listar: de documento a DTO
    CategoriaDTO toDTO(Categoria categoria);
}