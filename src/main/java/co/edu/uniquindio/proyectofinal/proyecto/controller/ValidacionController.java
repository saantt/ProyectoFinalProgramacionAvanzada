/* package co.edu.uniquindio.proyectofinal.proyecto.controller;

import co.edu.uniquindio.proyectofinal.proyecto.services.ValidacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/validacion")
@RequiredArgsConstructor
public class ValidacionController {

    private final ValidacionService validacionService;

    @PostMapping("/codigo")
    public ResponseEntity<Boolean> validarCodigo(
            @RequestParam String idUsuario,
            @RequestParam String codigo) {

        boolean esValido = validacionService.validarCodigo(codigo, idUsuario);

        return ResponseEntity.ok(esValido);
    }
}
 */