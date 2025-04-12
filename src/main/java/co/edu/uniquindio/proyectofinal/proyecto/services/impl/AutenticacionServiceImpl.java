package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import org.springframework.stereotype.Service;

import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.LoginRequestDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.TokenDTO;
import co.edu.uniquindio.proyectofinal.proyecto.services.AutenticacionService;

@Service
public class AutenticacionServiceImpl implements AutenticacionService {

     @Override
    public TokenDTO login(LoginRequestDTO loginRequest) throws Exception {
        return null;
}
}