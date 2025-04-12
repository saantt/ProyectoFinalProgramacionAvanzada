package co.edu.uniquindio.proyectofinal.proyecto.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoUsuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document("cuenta")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Cuenta {


    @Id
    private String id;

    private String email;
    private String password;
    private Rol rol;
    private LocalDateTime fechaRegistro;
    private Usuario usuario;
    EstadoUsuario estado;
    private CodigoValidacion codigoValidacionRegistro;
    private CodigoValidacion codigoValidacionPassword;
    private List<Reporte> reportes;
    
    private boolean activacionPrimeraVez;

    @Builder
    public Cuenta(String email, String password, Rol rol, LocalDateTime fechaRegistro, Usuario usuario, EstadoUsuario estado, CodigoValidacion codigoValidacionRegistro, CodigoValidacion codigoValidacionPassword) {
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.fechaRegistro = fechaRegistro;
        this.usuario = usuario;
        this.estado = estado;
        this.codigoValidacionRegistro = codigoValidacionRegistro;
        this.codigoValidacionPassword = codigoValidacionPassword;
        this.reportes = new ArrayList<>();
        this.activacionPrimeraVez = false;
    }

}
