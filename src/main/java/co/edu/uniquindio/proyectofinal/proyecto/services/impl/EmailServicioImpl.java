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

    @Value("Nombre Remitente") // O puedes ponerlo en properties: @Value("${mail.sender.name}")
    private String smtpName;

    // Setters
    public void setSmtpUsername(String smtpUsername) {
        this.smtpUsername = smtpUsername;
    }

    public void setSmtpName(String smtpName) {
        this.smtpName = smtpName;
    }

    @Override
    @Async("taskExecutor")
    public void enviarCorreo(EmailDTO emailDTO) throws Exception {
        if (emailDTO.destinatario() == null || emailDTO.destinatario().isBlank()) {
            throw new IllegalArgumentException("El destinatario del correo no puede ser nulo.");
        }

        Email email = EmailBuilder.startingBlank()
                .from(smtpUsername)
                .to(emailDTO.destinatario())
                .withSubject(emailDTO.asunto())
                .withHTMLText(emailDTO.cuerpo())
                .buildEmail();

        log.info("Enviando correo a {}", emailDTO.destinatario());
        mailer.sendMail(email);
    }
}