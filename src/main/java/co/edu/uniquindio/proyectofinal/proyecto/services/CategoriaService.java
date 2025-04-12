package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.dto.categoria.CategoriaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.categoria.InformacionCategoriaDTO;
import jakarta.validation.Valid;

import java.util.List;


/* public interface CategoriaService {
    List<CategoriaDTO> obtenerCategorias();
} */
public interface CategoriaService {
     void crearCategoria(CategoriaDTO categoria) throws Exception;
    void editarCategoria(String id, @Valid CategoriaDTO categoria) throws Exception;
    void eliminarCategoria(String id) throws Exception;
    InformacionCategoriaDTO obtenerCategoria(String id) throws Exception;
    List<CategoriaDTO> listar();
}


