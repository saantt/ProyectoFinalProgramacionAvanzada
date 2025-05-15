package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.LoginDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.TokenDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.ActivarCuentaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.CambiarPasswordDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.CrearCuentaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.EditarCuentaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.InformacionCuentaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.ItemCuentaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.Cuenta;
import co.edu.uniquindio.proyectofinal.proyecto.model.Reporte;
import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoUsuario;
import co.edu.uniquindio.proyectofinal.proyecto.repository.*;
import co.edu.uniquindio.proyectofinal.proyecto.services.CuentaServicio;
import co.edu.uniquindio.proyectofinal.proyecto.util.JWTUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // Lombok para inyección de dependencias
public class CuentaServicioImpl implements CuentaServicio {

    private final UsuarioRepository usuarioRepository;;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    @Override
    public TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception {
        Usuario usuario = usuarioRepository.findByEmail(loginDTO.email())
                .orElseThrow(() -> new Exception("Credenciales inválidas"));

        if (usuario.getEstado() != EstadoUsuario.ACTIVO) {
            throw new Exception("Debe activar su cuenta antes de iniciar sesión");
        }

        System.out.println("Password ingresado: " + loginDTO.password());
        System.out.println("Password almacenado: " + usuario.getPassword());

        if (!passwordEncoder.matches(loginDTO.password(), usuario.getPassword())) {
            throw new Exception("Credenciales inválidas");
        }

        return new TokenDTO(
                jwtUtils.generarToken(usuario.getId().toString(), usuario.getEmail(), usuario.getRol().name()));
    }

    @Override
    public String crearCuenta(CrearCuentaDTO cuenta) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'crearCuenta'");
    }

    @Override
    public String editarCuenta(EditarCuentaDTO cuenta) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editarCuenta'");
    }

    @Override
    public String eliminarCuenta(String id) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarCuenta'");
    }

    @Override
    public InformacionCuentaDTO obtenerInformacionCuenta(String id) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerInformacionCuenta'");
    }

    @Override
    public String enviarCodigoRecuperacionPassword(String email) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enviarCodigoRecuperacionPassword'");
    }

    @Override
    public String cambiarPassword(CambiarPasswordDTO cambiarPasswordDTO) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cambiarPassword'");
    }

    @Override
    public String activarCuenta(ActivarCuentaDTO activarCuentaDTO) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'activarCuenta'");
    }

    @Override
    public List<Cuenta> buscarReportesPorTitulo(String tituloReporte) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarReportesPorTitulo'");
    }

    @Override
    public List<Cuenta> buscarReportePorPropietario(String idPropietario) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarReportePorPropietario'");
    }

    @Override
    public Reporte obtenerDetalleBoleta(String idReporte, String idPropietario) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerDetalleBoleta'");
    }

    @Override
    public List<ItemCuentaDTO> listarCuentas() throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarCuentas'");
    }

    @Override
    public Cuenta obtenerPorEmail(String email) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerPorEmail'");
    }

    @Override
    public String enviarCodigoActivacionCuenta(String email) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enviarCodigoActivacionCuenta'");
    }

    @Override
    public void eliminarReportes(String id, List<Reporte> ReportesEliminar) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'eliminarReportes'");
    }
}