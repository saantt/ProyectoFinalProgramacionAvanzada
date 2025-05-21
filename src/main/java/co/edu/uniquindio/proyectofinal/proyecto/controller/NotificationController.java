package co.edu.uniquindio.proyectofinal.proyecto.controller;

import co.edu.uniquindio.proyectofinal.proyecto.dto.notificacion.NotificacionCreacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.notificacion.NotificacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Notificacion;
import co.edu.uniquindio.proyectofinal.proyecto.repository.NotificacionRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificationController {
        private final NotificacionRepository notificationRepository;

        @GetMapping("/usuario/{usuarioId}")
        public ResponseEntity<List<NotificacionDTO>> obtenerPorUsuario(
                        @PathVariable String usuarioId) {
                List<Notificacion> notificaciones = notificationRepository
                                .findByUsuarioIdOrderByFechaCreacionDesc(usuarioId);

                return ResponseEntity.ok(notificaciones.stream()
                                .map(NotificacionDTO::fromEntity) // Usa el método estático fromEntity
                                .collect(Collectors.toList()));
        }
}