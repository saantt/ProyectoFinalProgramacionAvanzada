    package co.edu.uniquindio.proyectofinal.proyecto.controller;

import co.edu.uniquindio.proyectofinal.proyecto.dto.validacion.CodigoValidacionDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.validacion.VerificacionCodigoDTO;
import co.edu.uniquindio.proyectofinal.proyecto.services.ValidacionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;





@RestController
@RequestMapping("/api/validaciones")
@RequiredArgsConstructor
@Tag(name = "Validaciones")
public class ValidacionController {

    private final ValidacionService validacionService;

    @PostMapping("/enviar")
    public ResponseEntity<Void> enviarCodigo(@RequestBody CodigoValidacionDTO dto) {
        validacionService.enviarCodigo(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/verificar")
    public ResponseEntity<Boolean> verificarCodigo(@RequestBody VerificacionCodigoDTO dto) {
        boolean valido = validacionService.verificarCodigo(dto);
        return ResponseEntity.ok(valido);
    }
}
