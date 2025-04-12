package co.edu.uniquindio.proyectofinal.proyecto.services;

import java.util.List;

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

public interface CuentaServicio {

    String crearCuenta(CrearCuentaDTO cuenta) throws Exception;

    String editarCuenta(EditarCuentaDTO cuenta) throws Exception;

    String eliminarCuenta(String id) throws Exception;

    InformacionCuentaDTO obtenerInformacionCuenta(String id) throws Exception;

    String enviarCodigoRecuperacionPassword(String email) throws Exception;

    String cambiarPassword(CambiarPasswordDTO cambiarPasswordDTO ) throws Exception;

    TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception;

    String activarCuenta(ActivarCuentaDTO activarCuentaDTO) throws Exception;


    ///NUEVA FUNCIONALIDAD
    List<Cuenta> buscarReportesPorTitulo(String tituloReporte) throws Exception;
    List<Cuenta> buscarReportePorPropietario(String idPropietario) throws Exception;
    Reporte obtenerDetalleBoleta(String idReporte, String idPropietario) throws Exception;

    List<ItemCuentaDTO> listarCuentas() throws Exception;

    Cuenta obtenerPorEmail(String email) throws Exception;

    String enviarCodigoActivacionCuenta(String email) throws Exception;

    void eliminarReportes(String id, List<Reporte> ReportesEliminar) throws  Exception;
}
