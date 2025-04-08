package co.edu.uniquindio.proyectofinal.proyecto.model;

import co.edu.uniquindio.proyectofinal.proyecto.model.common.Auditable;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoUsuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.Rol;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;
    private String nombre;
    private String correo;
    private String password;
    private Rol rol;

    public Usuario(String nombre, String correo, String password, Rol rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.rol = rol;
    }

}
