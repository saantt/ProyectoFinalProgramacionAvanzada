package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.dto.email.EmailDTO;

public interface EmailService {
    void enviarCorreo(EmailDTO emailDTO) throws Exception;
}