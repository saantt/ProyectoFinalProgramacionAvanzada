package co.edu.uniquindio.proyectofinal.proyecto.services;

import org.springframework.web.multipart.MultipartFile;
import java.util.Map;


public interface ImagenServicio {

    Map subirImagen(MultipartFile imagen) throws Exception;
    Map eliminarImagen(String idImagen) throws Exception;
}