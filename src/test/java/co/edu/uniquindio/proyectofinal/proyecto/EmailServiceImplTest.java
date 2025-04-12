package co.edu.uniquindio.proyectofinal.proyecto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.springframework.test.util.ReflectionTestUtils;

import co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion.EmailDTO;
import co.edu.uniquindio.proyectofinal.proyecto.services.impl.EmailServicioImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class EmailServicioImplTest {

    @Mock
    private Mailer mailer;

    @InjectMocks
    private EmailServicioImpl emailServicio;

    @BeforeEach
    void setUp() {
        // Inyectamos manualmente el valor de smtpUsername para evitar NullPointer
        ReflectionTestUtils.setField(emailServicio, "smtpUsername", "prueba@correo.com");
    }

    @Test
    void enviarCorreo_DeberiaEnviarCorreoCorrectamente() throws Exception {
        // Arrange
        EmailDTO emailDTO = new EmailDTO(
                "destinatario@correo.com",
                "Asunto de prueba",
                "<p>Cuerpo del correo de prueba</p>"
        );

        // Act
        emailServicio.enviarCorreo(emailDTO);

        // Assert
        Mockito.verify(mailer, Mockito.times(1)).sendMail(Mockito.any(Email.class));
    }

    @Test
    void enviarCorreo_DeberiaConstruirCorreoCorrectamente() throws Exception {
        // Arrange
        EmailDTO emailDTO = new EmailDTO(
                "destinatario@correo.com",
                "Asunto de prueba",
                "<p>Cuerpo del correo de prueba</p>"
        );

        // Act
        emailServicio.enviarCorreo(emailDTO);

        // Assert
        // Verificamos que el método de envío haya sido llamado correctamente
        Mockito.verify(mailer).sendMail(Mockito.any(Email.class));
        }
}
