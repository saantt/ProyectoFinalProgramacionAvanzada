package co.edu.uniquindio.proyectofinal.proyecto.dto.usuario;

import java.util.List;

import co.edu.uniquindio.proyectofinal.proyecto.model.Reporte;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoUsuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.Rol;

public record InformacionCuentaDTO(
        String id,
        String cedula,
        String nombre,
        String telefono,
        String direccion,
        String email,
        List<Reporte> reportes
) {

    public InformacionCuentaDTO(String id2, String nombre2, String email, List<Reporte> reportes2, String email2,
            String password, EstadoUsuario estado, Rol rol, String codigo, String codigo2) {
        this(id2, null, nombre2, null, null, email, reportes2);
        // Additional initialization logic can be added here if needed
    }

   
}

