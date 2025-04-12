package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.LoginDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.LoginRequestDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.TokenDTO;
import jakarta.validation.Valid;

public interface AutenticacionService {
   TokenDTO login(@Valid LoginRequestDTO loginRequestDTO) throws Exception;
}
