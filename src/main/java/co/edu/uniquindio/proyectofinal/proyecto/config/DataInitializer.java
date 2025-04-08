package co.edu.uniquindio.proyectofinal.proyecto.config;

import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.Rol;
import co.edu.uniquindio.proyectofinal.proyecto.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;

    public DataInitializer(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!usuarioRepository.existsByCorreo("milton@mail.com")) {
            Usuario usuario = new Usuario(
                    "Milton",
                    "milton@mail.com",
                    "123456",
                    Rol.ADMINISTRADOR // Asegúrate que Rol.USUARIO esté definido
            );
            usuarioRepository.save(usuario);
            System.out.println("✅ Usuario de prueba insertado correctamente.");
        } else {
            System.out.println("ℹ️ El usuario de prueba ya existe.");
        }
    }
}
