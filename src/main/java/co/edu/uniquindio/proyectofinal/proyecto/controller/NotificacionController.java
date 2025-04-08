    package co.edu.uniquindio.proyectofinal.proyecto.controller;


import co.edu.uniquindio.proyectofinal.proyecto.services.NotificacionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;






@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
@Tag(name = "Notificaciones")
public class NotificacionController {

    private final NotificacionService notificacionService;

    @GetMapping("/{idUsuario}")
    public ResponseEntity<Object> obtenerNotificaciones(@PathVariable String idUsuario) {
        return ResponseEntity.ok(notificacionService.obtenerNotificaciones(idUsuario));
    }
}
