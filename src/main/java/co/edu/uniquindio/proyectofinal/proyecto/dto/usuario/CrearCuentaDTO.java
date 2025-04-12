package co.edu.uniquindio.proyectofinal.proyecto.dto.usuario;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

import java.util.List;

public record CrearCuentaDTO(
        //La contaseña se puede hacer con expresion regular para validar que tenga mayuscula, minuscula, numero y caracter especial
        @NotBlank @Length(max = 10) String cedula,
        @NotBlank @Length(max = 100) String nombre,
        @NotBlank @Length(max = 10) String telefono,
        @Length(max = 80) String direccion,
        @NotBlank @Length(max = 40) @Email String email,
        @NotBlank @Length(min = 7, max = 20) String password
        
) {

}
