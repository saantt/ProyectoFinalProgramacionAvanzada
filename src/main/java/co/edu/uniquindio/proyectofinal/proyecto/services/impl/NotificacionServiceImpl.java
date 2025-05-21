package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import co.edu.uniquindio.proyectofinal.proyecto.dto.notificacion.NotificacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Notificacion;
import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.TipoNotificacion;
import co.edu.uniquindio.proyectofinal.proyecto.repository.NotificacionRepository;
import co.edu.uniquindio.proyectofinal.proyecto.repository.UsuarioRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.NotificationService;
import lombok.RequiredArgsConstructor;

import org.bson.types.ObjectId;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificationService {

    private final NotificacionRepository notificacionRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final JavaMailSender mailSender;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public void enviarNotificacion(Notificacion notificacion) {
        // 1. Guardar en BD
        Notificacion guardada = notificacionRepository.save(notificacion);

        // 2. Convertir a DTO
        NotificacionDTO dto = NotificacionDTO.fromEntity(guardada);

        // 3. Enviar por WebSocket
        messagingTemplate.convertAndSendToUser(
                guardada.getUsuarioId(),
                "/queue/notificaciones",
                dto);

        // 4. Enviar email si está configurado
        if (guardada.isEnviarEmail()) {
            enviarEmail(dto);
        }
    }

    @Override
    @Transactional
    public void crearYEnviarNotificacion(String titulo, String mensaje, String usuarioId,
            String tipoNotificacion, boolean enviarEmail) {
        // Obtener email del usuario
        ObjectId usuarioObjectId = new ObjectId(usuarioId);

        // Obtener email del usuario
        String usuarioEmail = usuarioRepository.findById(usuarioObjectId)
                .map(Usuario::getEmail)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear entidad
        Notificacion notificacion = Notificacion.builder()
                .titulo(titulo)
                .mensaje(mensaje)
                .usuarioId(usuarioId)
                .usuarioEmail(usuarioEmail)
                .tipoNotificacion(tipoNotificacion)
                .fechaCreacion(LocalDateTime.now())
                .leida(false)
                .enviarEmail(enviarEmail)
                .build();

        // Usar el método principal
        enviarNotificacion(notificacion);
    }

    private void enviarEmail(NotificacionDTO notificacion) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(notificacion.getUsuarioEmail());
        email.setSubject(notificacion.getTitle());
        email.setText(notificacion.getMessage());
        mailSender.send(email);
    }
}