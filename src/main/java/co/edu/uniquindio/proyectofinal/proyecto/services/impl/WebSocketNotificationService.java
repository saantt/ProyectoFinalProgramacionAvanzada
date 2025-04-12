package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.proyectofinal.proyecto.dto.notificacion.NotificacionDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WebSocketNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void notificarClientes(NotificacionDTO notificacionDTO) {
        // Envía un mensaje a todos los clientes suscritos al tema "/topic/reports"
        messagingTemplate.convertAndSend("/topic/reports", notificacionDTO);
    }

    public void notificarClienteEspecifico(String userId, NotificacionDTO notificacion) {
        messagingTemplate.convertAndSend("/topic/user/" + userId + "/reports", notificacion);
    }
}
