package co.edu.uniquindio.proyectofinal.proyecto.model;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoUsuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.Rol;

@Document("usuarios")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;

    private String nombre;
    private String email;
    private String telefono;
    private String ciudad;
    private Rol rol;
    private EstadoUsuario estado; 
    private String password;
    private CodigoValidacion codigoValidacion; 
    private String fechaRegistro;


    
    @Builder
    public Usuario(String nombre, String email, String telefono, String ciudad, Rol rol, EstadoUsuario estado, String password, CodigoValidacion codigoValidacion, String fechaRegistro) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.rol = rol;
        this.estado = estado;
        this.password = password;
        this.codigoValidacion = codigoValidacion;
        this.fechaRegistro = fechaRegistro;
    }
}