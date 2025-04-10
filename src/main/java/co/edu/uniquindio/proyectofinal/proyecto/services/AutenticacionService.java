package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.LoginRequestDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.LoginResponseDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.TokenDTO;

public interface AutenticacionService {
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO) throws Exception;
}
