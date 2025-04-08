package co.edu.uniquindio.proyectofinal.proyecto.controller;
import co.edu.uniquindio.proyectofinal.proyecto.dto.MensajeDTO; 
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.UsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<MensajeDTO<String>> crearUsuario(@RequestBody UsuarioDTO usuarioDTO) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, usuarioService.crearUsuario(usuarioDTO)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeDTO<UsuarioDTO>> obtenerUsuario(@PathVariable String id) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, usuarioService.obtenerUsuario(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>> actualizarUsuario(@PathVariable String id, @RequestBody UsuarioDTO usuarioDTO) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, usuarioService.actualizarUsuario(id, usuarioDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeDTO<String>> eliminarUsuario(@PathVariable String id) throws Exception {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, usuarioService.eliminarUsuario(id)));
    }

    @GetMapping
    public ResponseEntity<MensajeDTO<List<UsuarioDTO>>> listarUsuarios() {
        return ResponseEntity.ok().body(new MensajeDTO<>(false, usuarioService.listarUsuarios()));
    }
}
