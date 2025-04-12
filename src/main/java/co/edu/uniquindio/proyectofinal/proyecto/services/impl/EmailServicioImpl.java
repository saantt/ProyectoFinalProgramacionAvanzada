package co.edu.uniquindio.proyectofinal.proyecto.services.impl;
import jakarta.mail.MessagingException;
import jakarta.validation.constraints.Email;

import org.springframework.mail.javamail.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion.EmailDTO;
import co.edu.uniquindio.proyectofinal.proyecto.services.EmailService;

import java.io.ByteArrayOutputStream;
import com.google.zxing.WriterException;

import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.mail.SimpleMailMessage;


@Service
public class EmailServicioImpl implements EmailService{

    @Override
    @Async
    public void enviaremail(EmailDTO emailDTO) throws Exception {


        Email email = (Email) EmailBuilder.startingBlank()
                .from("santiagomaring05@gmail.com")
                .to(emailDTO.destinatario())
                .withSubject(emailDTO.asunto())
                .withPlainText(emailDTO.cuerpo())

                .buildEmail();


        //unieventosfae@gmail.com
        //fae12345
        //clave de aplicación: yygy ngcd lulw oxjk
        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "santiagomaring05@gmail.com", "santinew890")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {

            mailer.sendMail((org.simplejavamail.api.email.Email) email);
        }


    }

    @Override
    @Async
    public void enviaremailHtml(EmailDTO emailDTO) throws Exception {


        Email email = (Email) EmailBuilder.startingBlank()
                .from("santiagomaring05@gmail.com")
                .to(emailDTO.destinatario())
                .withSubject(emailDTO.asunto())
                .appendTextHTML(emailDTO.cuerpo())

                .buildEmail();


        //unieventosfae@gmail.com
        //fae12345
        //clave de aplicación: yygy ngcd lulw oxjk
        try (Mailer mailer = MailerBuilder
                .withSMTPServer("smtp.gmail.com", 587, "santiagomaring05@gmail.com", "santinew890")
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer()) {

            mailer.sendMail((org.simplejavamail.api.email.Email) email);
        }


    }

   
   

    
   


}

