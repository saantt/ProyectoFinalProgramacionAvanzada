package co.edu.uniquindio.proyectofinal.proyecto.config;

import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Bean
    public Mailer mailer() {
        return MailerBuilder
                .withSMTPServer(host, port, username, password)
                .withSessionTimeout(10_000) // 10 segundos
                .clearEmailValidator() // Desactiva validación estricta
                // Removed invalid method call
                .withDebugLogging(true) // Más logs para diagnóstico
                .async()
                .buildMailer();
    }
}