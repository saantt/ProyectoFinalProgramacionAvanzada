package co.edu.uniquindio.proyectofinal.proyecto;

import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyecto.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoApplication  implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    public static void main(String[] args) {
        SpringApplication.run(ProyectoApplication .class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Aquí va el código que quieres ejecutar al iniciar la aplicación
        System.out.println("La aplicación se ha iniciado correctamente.");
    }

    
}
