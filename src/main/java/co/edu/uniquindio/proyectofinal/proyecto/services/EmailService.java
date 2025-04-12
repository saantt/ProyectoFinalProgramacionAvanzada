package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion.EmailDTO;

public interface EmailService {

    public void enviarCorreo(EmailDTO emailDTO) throws Exception;
    

}
