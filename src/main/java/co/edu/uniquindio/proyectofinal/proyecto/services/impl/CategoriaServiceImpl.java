package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import co.edu.uniquindio.proyectofinal.proyecto.dto.categoria.CategoriaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Categoria;
import co.edu.uniquindio.proyectofinal.proyecto.repository.CategoriaRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
public List<CategoriaDTO> obtenerCategorias() {
    List<Categoria> categorias = categoriaRepository.findAll();
    return categorias.stream()
        .map(c -> new CategoriaDTO(c.getId(), c.getNombre(), null))
        .toList();
}
}

