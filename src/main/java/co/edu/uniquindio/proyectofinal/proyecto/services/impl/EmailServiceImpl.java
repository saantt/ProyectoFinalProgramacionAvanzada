package co.edu.uniquindio.proyectofinal.proyecto.services.impl;
import jakarta.mail.MessagingException;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.proyectofinal.proyecto.services.EmailService;

import org.springframework.mail.SimpleMailMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender sender;

    public EmailServiceImpl(JavaMailSender sender) {
        this.sender = sender;
    }

    @Override
    public void enviarCorreo(String destino, String asunto, String cuerpo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destino);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);
        sender.send(mensaje);
    }
}
