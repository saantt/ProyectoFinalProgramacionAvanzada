package co.edu.uniquindio.proyectofinal.proyecto.dto.categoria;

import org.bson.types.ObjectId;

import jakarta.validation.constraints.NotBlank;

public record CategoriaDTO (
    @NotBlank(message = "El nombre es obligatorio")
    String nombre,
    String icono 
){

    public CategoriaDTO(ObjectId id1, String nombre2, String icono2) {
        this(nombre2, icono2);
        // Additional logic can be added here if needed
    }

}