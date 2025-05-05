package co.edu.uniquindio.proyectofinal.proyecto.config;

import co.edu.uniquindio.proyectofinal.proyecto.security.JwtAuthenticationFilter;
import co.edu.uniquindio.proyectofinal.proyecto.security.config.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .authorizeHttpRequests(auth -> auth
                        // Endpoints públicos (sin autenticación)
                        .requestMatchers(
                                "/api/auth/**", // Autenticación
                                "/v3/api-docs/**", // Documentación OpenAPI
                                "/swagger-ui/**", // Interfaz Swagger UI
                                "/api/diagnostico/**", // Prueba Token
                                "/swagger-ui.html", // Interfaz Swagger UI HTML
                                "/swagger-resources/**", // Recursos Swagger
                                "/webjars/**" // WebJars para Swagger
                        ).permitAll()

                        // Endpoints de usuarios
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll() // Registro abierto
                        .requestMatchers(HttpMethod.GET, "/api/usuarios").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/**")
                        .hasAnyAuthority("CIUDADANO", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/**")
                        .hasAnyAuthority("CIUDADANO", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasAuthority("ADMINISTRADOR")

                        // Endpoints de reportes
                        .requestMatchers(HttpMethod.GET, "/api/reportes/**")
                        .hasAnyAuthority("CIUDADANO", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/api/reportes")
                        .hasAnyAuthority("CIUDADANO", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/api/reportes/**")
                        .hasAnyAuthority("CIUDADANO", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/reportes/**").hasAuthority("ADMINISTRADOR")

                        // Endpoints de comentarios
                        .requestMatchers(HttpMethod.GET, "/api/comentarios/**")
                        .hasAnyAuthority("CIUDADANO", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.POST, "/api/comentarios/**")
                        .hasAnyAuthority("CIUDADANO", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/api/comentarios/**")
                        .hasAnyAuthority("CIUDADANO", "ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/comentarios/**").hasAuthority("ADMINISTRADOR")

                        // Endpoints de categorías
                        .requestMatchers(HttpMethod.GET, "/api/categorias").permitAll() // Listado público
                        .requestMatchers(HttpMethod.GET, "/api/categorias/**").permitAll() // Detalle público
                        .requestMatchers(HttpMethod.POST, "/api/categorias").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.PUT, "/api/categorias/**").hasAuthority("ADMINISTRADOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/categorias/**").hasAuthority("ADMINISTRADOR")

                        // Todos los demás endpoints requieren autenticación
                        .anyRequest().authenticated())

                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*")); // Cambiar por dominios específicos en producción
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}