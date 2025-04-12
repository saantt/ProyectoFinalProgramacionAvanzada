package co.edu.uniquindio.proyectofinal.proyecto.config;

import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
@Configuration
public class MailerConfig {

    @Autowired
    private JavaMailSender javaMailSender;

    @Bean
    public Mailer mailer() {
        // Puedes construir tu Mailer con los valores directamente desde javaMailSender si es compatible,
        // o incluso usar directamente javaMailSender si no necesitas simple-java-mail.
        return MailerBuilder
                .withSMTPServer(
                    ((JavaMailSenderImpl) javaMailSender).getHost(),
                    ((JavaMailSenderImpl) javaMailSender).getPort(),
                    ((JavaMailSenderImpl) javaMailSender).getUsername(),
                    ((JavaMailSenderImpl) javaMailSender).getPassword())
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .withDebugLogging(true)
                .buildMailer();
    }
}
