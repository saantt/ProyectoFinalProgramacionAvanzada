package co.edu.uniquindio.proyectofinal.proyecto.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Plataforma Seguridad Ciudadana")
                        .description("Documentación de la API de tu proyecto de grado")
                        .version("1.0"));
    }
}
