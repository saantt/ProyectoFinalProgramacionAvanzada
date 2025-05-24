package co.edu.uniquindio.proyectofinal.proyecto.config;

import co.edu.uniquindio.proyectofinal.proyecto.model.Categoria;
import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoUsuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.Rol;
import co.edu.uniquindio.proyectofinal.proyecto.repository.CategoriaRepository;
import co.edu.uniquindio.proyectofinal.proyecto.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;

    public DataInitializer(UsuarioRepository usuarioRepository, CategoriaRepository categoriaRepository,
            BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.categoriaRepository = categoriaRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!usuarioRepository.existsByEmail("milton@mail.com")) {
            Usuario usuario = new Usuario(
                    "Milton Gómez",
                    "milton@gmail.com",
                    "3206991690",
                    "Armenia", Rol.ADMINISTRADOR, EstadoUsuario.ACTIVO, passwordEncoder.encode("123456789"), null,
                    null);
            usuarioRepository.save(usuario);
            System.out.println("Usuario creado EN ATLAS");
            System.out.println("✅ Usuario de prueba insertado correctamente.");
        } else {
            System.out.println("ℹ️ El usuario de prueba ya existe.");
        }

        if (!categoriaRepository.existsByNombre("Seguridad")) {
            Categoria categoria = new Categoria();
            categoria.setNombre("Seguridad");
            categoriaRepository.save(categoria);
            System.out.println("✅ Categoría de prueba insertada correctamente.");
        } else {
            System.out.println("ℹ️ La categoría de prueba ya existe.");
        }
    }
}
