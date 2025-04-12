package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import co.edu.uniquindio.proyectofinal.proyecto.dto.categoria.CategoriaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.categoria.InformacionCategoriaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Categoria;
import co.edu.uniquindio.proyectofinal.proyecto.repository.CategoriaRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.CategoriaService;
import co.edu.uniquindio.proyectofinal.proyecto.util.CategoriaMapper;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepositorio;
    private final CategoriaMapper categoriaMapper;

    @Autowired
    public CategoriaServiceImpl(CategoriaRepository categoriaRepositorio, CategoriaMapper categoriaMapper) {
        this.categoriaRepositorio = categoriaRepositorio;
        this.categoriaMapper = categoriaMapper;
    }


    @Override
    public void crearCategoria(CategoriaDTO dto) throws Exception {
        // Validar si ya existe una categoría con el mismo nombre (opcional pero recomendable)
        if (categoriaRepositorio.existsByNombreIgnoreCase(dto.nombre())) {
            throw new Exception("Ya existe una categoría con el nombre: " + dto.nombre());
        }
        Categoria categoria = categoriaMapper.toDocument(dto);
        categoriaRepositorio.save(categoria);
    }

    @Override
    public void editarCategoria(String id, CategoriaDTO categoriaDTO) throws Exception {
        Categoria categoriaExistente = categoriaRepositorio.findById(new ObjectId(id))
                .orElseThrow(() -> new Exception("La categoría con ID " + id + " no existe"));
        boolean existeOtra = categoriaRepositorio.existsByNombreIgnoreCase(categoriaDTO.nombre())
                && !categoriaExistente.getNombre().equalsIgnoreCase(categoriaDTO.nombre());
        if (existeOtra) {
            throw new Exception("Ya existe una categoría con el nombre: " + categoriaDTO.nombre());
        }
        categoriaExistente.setNombre(categoriaDTO.nombre());
        categoriaExistente.setIcono(categoriaDTO.icono());
        categoriaRepositorio.save(categoriaExistente);
    }

    @Override
    public void eliminarCategoria(String id) throws Exception {
        ObjectId objectId = new ObjectId(id);
        if (!categoriaRepositorio.existsById(objectId)) {
            throw new Exception("No se encontró una categoría con el ID: " + objectId);
        }
        categoriaRepositorio.deleteById(objectId);
    }

    @Override
    public List<CategoriaDTO> listar() {
        return categoriaRepositorio.findAll()
                .stream()
                .map(categoriaMapper::toDTO)
                .toList();
    }

    @Override
    public InformacionCategoriaDTO obtenerCategoria(String id) throws Exception {
        return null;
    }
}

