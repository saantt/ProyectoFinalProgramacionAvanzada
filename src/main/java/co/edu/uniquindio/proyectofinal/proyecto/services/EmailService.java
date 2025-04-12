package co.edu.uniquindio.proyectofinal.proyecto.services;

import co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion.EmailDTO;

public interface EmailService {

    void enviaremail(EmailDTO emailDTO) throws Exception;
    void enviaremailHtml(EmailDTO emailDTO) throws Exception;
    

}
