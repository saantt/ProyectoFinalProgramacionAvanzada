package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.dto.validacion.*;

public interface CodigoValidacionService {
    void enviarCodigo(String email);
    boolean verificarCodigo(VerificacionCodigoDTO dto);
}
