package co.edu.uniquindio.proyectofinal.proyecto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.springframework.test.util.ReflectionTestUtils;

import co.edu.uniquindio.proyectofinal.proyecto.dto.email.EmailDTO;
import co.edu.uniquindio.proyectofinal.proyecto.services.impl.EmailServicioImpl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServicioImplTest {

    @Mock
    private Mailer mailer;

    @InjectMocks
    private EmailServicioImpl emailServicio;

    @BeforeEach
    void setUp() {
        // Configuración para pruebas
        emailServicio = new EmailServicioImpl(mailer);
        emailServicio.setSmtpUsername("prueba@correo.com");
        emailServicio.setSmtpName("Nombre Remitente");
    }

    @Test
    void enviarCorreo_DeberiaEnviarCorreoCorrectamente() throws Exception {
        EmailDTO emailDTO = new EmailDTO(
                "Asunto de prueba",
                "<p>Cuerpo del correo de prueba</p>",
                "destinatario@correo.com");

        emailServicio.enviarCorreo(emailDTO);

        verify(mailer, times(1)).sendMail(any(Email.class));
    }

    @Test
    void enviarCorreo_DeberiaConstruirCorreoCorrectamente() throws Exception {
        EmailDTO emailDTO = new EmailDTO(
                "Asunto de prueba",
                "<p>Cuerpo del correo de prueba</p>",
                "destinatario@correo.com");

        ArgumentCaptor<Email> emailCaptor = ArgumentCaptor.forClass(Email.class);

        emailServicio.enviarCorreo(emailDTO);

        verify(mailer).sendMail(emailCaptor.capture());
        Email emailEnviado = emailCaptor.getValue();

        assertEquals("Asunto de prueba", emailEnviado.getSubject());
        assertEquals("destinatario@correo.com", emailEnviado.getRecipients().get(0).getAddress());
        assertEquals("prueba@correo.com", emailEnviado.getFromRecipient().getAddress());
        assertTrue(emailEnviado.getHTMLText().contains("Cuerpo del correo de prueba"));
    }
}