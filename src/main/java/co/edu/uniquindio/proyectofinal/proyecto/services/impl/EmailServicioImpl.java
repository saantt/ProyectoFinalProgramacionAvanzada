package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.proyectofinal.proyecto.dto.email.EmailDTO;
import co.edu.uniquindio.proyectofinal.proyecto.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServicioImpl implements EmailService {

    private final Mailer mailer;

    @Value("${spring.mail.username}")
    private String smtpUsername;

    @Override
    @Async("taskExecutor")
    public void enviarCorreo(EmailDTO emailDTO) throws Exception {
        try {
            if (emailDTO.destinatario() == null || emailDTO.destinatario().isBlank()) {
                throw new IllegalArgumentException("El destinatario no puede estar vacío");
            }

            Email email = EmailBuilder.startingBlank()
                    .from("Tu Aplicación", smtpUsername)
                    .to(emailDTO.destinatario())
                    .withSubject(emailDTO.asunto())
                    .withHTMLText(emailDTO.cuerpo())
                    .buildEmail();

            log.info("Intentando enviar correo a {}", emailDTO.destinatario());
            mailer.sendMail(email);
            log.info("Correo enviado exitosamente a {}", emailDTO.destinatario());

        } catch (Exception e) {
            log.error("Error enviando correo a {}: {}", emailDTO.destinatario(), e.getMessage());
            throw new Exception("Error al enviar el correo: " + e.getMessage());
        }
    }
}