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

import static org.awaitility.Awaitility.await;
import static java.util.concurrent.TimeUnit.SECONDS;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @Mock
    private Mailer mailer;

    @InjectMocks
    private EmailServicioImpl emailServicio;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(emailServicio, "smtpUsername", "prueba@correo.com");
        ReflectionTestUtils.setField(emailServicio, "smtpName", "Nombre Remitente");
    }

    @Test
    void enviarCorreo_DeberiaEnviarCorreoCorrectamente() throws Exception {
        // Arrange
        EmailDTO emailDTO = new EmailDTO("Asunto", "<p>Cuerpo</p>", "dest@test.com");

        // Act
        emailServicio.enviarCorreo(emailDTO);

        // Assert
        await().atMost(2, SECONDS)
                .untilAsserted(() -> verify(mailer).sendMail(any(Email.class)));
    }

    @Test
    void enviarCorreo_DeberiaLanzarExcepcionCuandoDestinatarioEsNulo() {
        // Arrange
        EmailDTO emailDTO = new EmailDTO("Asunto", "<p>Cuerpo</p>", null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            emailServicio.enviarCorreo(emailDTO);
        });

        verify(mailer, never()).sendMail(any());
    }

    @Test
    void enviarCorreo_DeberiaConstruirCorreoCorrectamente() throws Exception {
        // Arrange
        EmailDTO emailDTO = new EmailDTO(
                "Asunto de prueba",
                "<p>Cuerpo del correo de prueba</p>",
                "destinatario@correo.com");

        // Act
        emailServicio.enviarCorreo(emailDTO);

        // Assert
        ArgumentCaptor<Email> emailCaptor = ArgumentCaptor.forClass(Email.class);

        await().atMost(2, SECONDS).untilAsserted(() -> {
            verify(mailer).sendMail(emailCaptor.capture());
            Email emailEnviado = emailCaptor.getValue();

            assertAll(
                    () -> assertEquals("Asunto de prueba", emailEnviado.getSubject()),
                    () -> assertEquals("destinatario@correo.com",
                            emailEnviado.getRecipients().get(0).getAddress()),
                    () -> assertEquals("prueba@correo.com",
                            emailEnviado.getFromRecipient().getAddress()),
                    () -> assertTrue(emailEnviado.getHTMLText()
                            .contains("Cuerpo del correo de prueba")));
        });
    }

    @Test
    void enviarCorreo_DeberiaLanzarExcepcionCuandoDestinatarioEstaVacio() {
        // Arrange
        EmailDTO emailDTO = new EmailDTO("Asunto", "<p>Cuerpo</p>", "   ");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            emailServicio.enviarCorreo(emailDTO);
        });

        verify(mailer, never()).sendMail(any());
    }
}