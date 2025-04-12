package co.edu.uniquindio.proyectofinal.proyecto.services.impl;  
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.internal.MailerImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.proyectofinal.proyecto.dto.email.EmailDTO;
import co.edu.uniquindio.proyectofinal.proyecto.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServicioImpl implements EmailService {

    private final Mailer mailer;

    

    @Override
    @Async("taskExecutor")
    public void enviarCorreo(co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion.EmailDTO emailDTO)
            throws Exception {
                if (emailDTO.destinatario() == null || emailDTO.destinatario().isBlank()) {
                    throw new IllegalArgumentException("El destinatario del correo no puede ser nulo.");
                }
        
                String fromEmail = ((MailerImpl) mailer).getServerConfig().getUsername();
        
                Email email = EmailBuilder.startingBlank()
                        .from(fromEmail)
                        .to(emailDTO.destinatario())
                        .withSubject(emailDTO.asunto())
                        .withHTMLText(emailDTO.cuerpo())
                        .buildEmail();
        
                log.info("Enviando correo a {}", emailDTO.destinatario());
        
                mailer.sendMail(email);
    }
}

    
   




