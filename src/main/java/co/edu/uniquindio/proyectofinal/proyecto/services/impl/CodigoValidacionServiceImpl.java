package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import co.edu.uniquindio.proyectofinal.proyecto.model.CodigoValidacion;
import co.edu.uniquindio.proyectofinal.proyecto.repository.CodigoValidacionRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.CodigoValidacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CodigoValidacionServiceImpl implements CodigoValidacionService {

    private final CodigoValidacionRepository codigoValidacionRepo;

    @Override
    public CodigoValidacion guardar(CodigoValidacion codigo) {
        // Elimina cualquier código existente para este email
        eliminarCodigoExistente(codigo.getEmail());

        // Establece fecha de creación
        codigo.setFechaCreacion(LocalDateTime.now());

        return codigoValidacionRepo.save(codigo);
    }

    @Override
    public CodigoValidacion obtenerPorCodigo(String codigo) {
        return codigoValidacionRepo.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("Código no encontrado"));
    }

    public Optional<CodigoValidacion> obtenerPorEmail(String email) {
        return codigoValidacionRepo.findByEmail(email);
    }

    public void eliminarCodigoExistente(String email) {
        codigoValidacionRepo.findByEmail(email)
                .ifPresent(codigo -> codigoValidacionRepo.delete(codigo));
    }

    public CodigoValidacion generarNuevoCodigo(String email) {
        String codigo = generarCodigoAleatorio();

        CodigoValidacion codigoValidacion = CodigoValidacion.builder()
                .codigo(codigo)
                .email(email)
                .fechaCreacion(LocalDateTime.now())
                .build();

        return guardar(codigoValidacion);
    }

    public boolean validarCodigo(String email, String codigo) {
        Optional<CodigoValidacion> codigoOptional = codigoValidacionRepo.findByEmailAndCodigo(email, codigo);

        if (codigoOptional.isPresent()) {
            CodigoValidacion codigoValidacion = codigoOptional.get();
            // Verificar que el código no haya expirado (ej. 15 minutos)
            boolean expirado = LocalDateTime.now().isAfter(
                    codigoValidacion.getFechaCreacion().plusMinutes(15));

            if (!expirado) {
                codigoValidacionRepo.delete(codigoValidacion);
                return true;
            }
        }
        return false;
    }

    private String generarCodigoAleatorio() {
        // Genera un código de 6 dígitos numéricos
        return String.format("%06d", new Random().nextInt(999999));
    }
}