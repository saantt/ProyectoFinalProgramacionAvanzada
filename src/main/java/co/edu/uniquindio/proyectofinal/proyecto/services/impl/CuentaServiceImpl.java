/* /* /* package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.LoginDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.auth.TokenDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.ubicacion.EmailDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.ActivarCuentaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.CambiarPasswordDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.CrearCuentaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.EditarCuentaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.InformacionCuentaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.ItemCuentaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.model.CodigoValidacion;
import co.edu.uniquindio.proyectofinal.proyecto.model.Cuenta;
import co.edu.uniquindio.proyectofinal.proyecto.model.Reporte;
import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoUsuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.Rol;
import co.edu.uniquindio.proyectofinal.proyecto.repository.CuentaRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.CuentaServicio;
import co.edu.uniquindio.proyectofinal.proyecto.services.EmailService;
import co.edu.uniquindio.proyectofinal.proyecto.util.JWTUtils;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CuentaServiceImpl implements CuentaServicio {

    private final CuentaRepository cuentaRepo;
    private final JWTUtils jwtUtils;
    private final EmailService emailServicio;

    
    //private final FutureOrPresentValidatorForLocalDateTime futureOrPresentValidatorForLocalDateTime;


    @Override
    public String crearCuenta(CrearCuentaDTO cuenta) throws Exception {


       if( existeEmail(cuenta.email())){

            throw new Exception("Ya existe un usuario registrado con el email "+cuenta.email());

       }

       if( existeCedula(cuenta.cedula())){

           throw new Exception("La cédula " + cuenta.cedula() + " ya se encuentra registrada.");

       }

        String codigoAleatorio = generarCodigo();

        Cuenta nuevaCuenta = new Cuenta();
        nuevaCuenta.setEmail(cuenta.email());
        nuevaCuenta.setPassword(encriptarPassword(cuenta.password()));
        nuevaCuenta.setRol(Rol.CIUDADANO);
        nuevaCuenta.setFechaRegistro(LocalDateTime.now());
        nuevaCuenta.setUsuario(new Usuario(
                cuenta.cedula(),
                cuenta.nombre(),
                cuenta.telefono()
                , cuenta.direccion()
                , cuenta.email(),
                cuenta.password()
                
                
        ));
        nuevaCuenta.setEstado(EstadoUsuario.INACTIVO);
        nuevaCuenta.setCodigoValidacionRegistro(
                new CodigoValidacion(
                        LocalDateTime.now(), codigoAleatorio, codigoAleatorio
                ));
        nuevaCuenta.setReportes(new ArrayList<>());
        

        
        
        emailServicio.enviaremail( new EmailDTO("CODIGO DE ACTIVACIÓN CUENTA", nuevaCuenta.getCodigoValidacionRegistro().getCodigo(), nuevaCuenta.getEmail()) );
        return "Su cuenta se ha generado con éxito.";
    }

    @Override
    public String editarCuenta(EditarCuentaDTO cuenta) throws Exception {

        //Si no se encontró la cuenta del usuario, lanzamos una excepción
        if(!existeCuenta(cuenta.id())){
            throw new Exception("No se encontró una cuenta con el id "+cuenta.id());
        }


        Cuenta cuentaModificada = obtenerCuenta(cuenta.id());
        cuentaModificada.getUsuario().setNombre( cuenta.nombre());
        

        cuentaRepo.save(cuentaModificada);
        return cuentaModificada.getId();
    }


    @Override
    public String eliminarCuenta(String id) throws Exception {

        if(!existeCuenta(id)){
            throw new Exception("No se encontró una cuenta con el id " + id);
        }

        Cuenta cuenta = obtenerCuenta(id);

        cuenta.setEstado(EstadoUsuario.ELIMINADO);

        cuentaRepo.save(cuenta);

        return "Su cuenta ha sido eliminada.";
    }

    @Override
    @Transactional (readOnly = true)
    public InformacionCuentaDTO obtenerInformacionCuenta(String id) throws Exception {

        Cuenta cuenta = obtenerCuenta(id);

        return new InformacionCuentaDTO(
                id,
                cuenta.getUsuario().getNombre(),
                cuenta.getEmail(),
                cuenta.getReportes(),                
                cuenta.getUsuario().getEmail(),
                cuenta.getUsuario().getPassword(),                
                cuenta.getEstado(),
                cuenta.getRol(),
                cuenta.getCodigoValidacionRegistro().getCodigo(),
                cuenta.getCodigoValidacionPassword().getCodigo()            
        );

    }

    @Override
    public String enviarCodigoRecuperacionPassword(String email) throws Exception {

        Cuenta cuenta = obtenerEmail(email);
        String codigoValidacion = generarCodigo();

        cuenta.setCodigoValidacionPassword(new CodigoValidacion(
                LocalDateTime.now(),
                codigoValidacion, codigoValidacion
                ));

        cuentaRepo.save(cuenta);

        emailServicio.enviaremail( new EmailDTO("CODIGO DE RECUPERACION DE CONTRASEÑA", codigoValidacion, email) );

        return "Se ha enviado un email con el código de recuperación de contraseña";

    }

    @Override
    public String cambiarPassword(CambiarPasswordDTO cambiarPasswordDTO) throws Exception {

        Cuenta cuentaOptional = obtenerEmail(cambiarPasswordDTO.email());

        CodigoValidacion codigoValidacion = cuentaOptional.getCodigoValidacionPassword();

        if(codigoValidacion.getCodigo().equals(cambiarPasswordDTO.codigoVerificacion())){
            if(codigoValidacion.getFechaCreacion().plusMinutes(15).isAfter(LocalDateTime.now())){
                cuentaOptional.setPassword(encriptarPassword(cambiarPasswordDTO.passwordNueva()));
                cuentaRepo.save(cuentaOptional);
            }else{
                throw new Exception("El código ya expiró.");
            }
        }else{
            throw new Exception("El código ingresado no coincide con el enviado al email.");
        }

        return "Su contraseña ha sido cambiada.";
    }

    @Override
    public TokenDTO iniciarSesion(LoginDTO loginDTO) throws Exception {

        Cuenta cuenta = obtenerPorEmail(loginDTO.email());
        if(cuenta.getEstado() == EstadoUsuario.ACTIVO){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            if( !passwordEncoder.matches(loginDTO.password(), cuenta.getPassword()) ) {
                throw new Exception("La contraseña es incorrecta");
            }

            Map<String, Object> map = construirClaims(cuenta);
            return new TokenDTO( jwtUtils.generarToken(cuenta.getEmail(), map) );
        }else {
            throw new Exception("La cuenta no esta activa");
        }

    }


    @Override
    public String activarCuenta(ActivarCuentaDTO activarCuentaDTO) throws Exception {
        // Buscar la cuenta por el token de validación de registro
        Optional<Cuenta> cuentaOpt = cuentaRepo.buscarPorCodigoValidacion(activarCuentaDTO.token());

        // Verificar si la cuenta existe
        if (!cuentaOpt.isPresent()) {
            throw new Exception("El token de activación es inválido.");
        }

        if (cuentaOpt.get().getEstado() == EstadoUsuario.ACTIVO){
            throw new Exception("La cuenta ya está activa.");
        }

        Cuenta cuenta = cuentaOpt.get();
        // Verificar si el tiempo desde la creación del token ha superado los 15 minutos
        LocalDateTime fechaCreacionToken = cuenta.getCodigoValidacionRegistro().getFechaCreacion();
        if (fechaCreacionToken.plusMinutes(15).isBefore(LocalDateTime.now())) {
            throw new Exception("El token de activación ha expirado.");
        }

        // Activar la cuenta si el token es válido y no ha expirado
        cuenta.setEstado(EstadoUsuario.ACTIVO);
        if(!cuentaOpt.get().isActivacionPrimeraVez()){
            cuentaOpt.get().setActivacionPrimeraVez(true);
            cuentaOpt.get().setCodigoValidacionRegistro(null);
            cuentaOpt.get().setCodigoValidacionPassword(null);  
            cuentaRepo.save(cuentaOpt.get());   

            String cuerpo = "<!DOCTYPE html>\n" +
                    "<html lang=\"es\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Activación de Cuenta</title>\n" +
                    "    <style>\n" +
                    "        body {\n" +
                    "            font-family: Arial, sans-serif;\n" +
                    "            background-color: #f4f4f4;\n" +
                    "            color: #333;\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "        }\n" +
                    "        .container {\n" +
                    "            width: 100%;\n" +
                    "            max-width: 600px;\n" +
                    "            margin: 0 auto;\n" +
                    "            background-color: #fff;\n" +
                    "            padding: 20px;\n" +
                    "            border-radius: 8px;\n" +
                    "            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);\n" +
                    "        }\n" +
                    "        .header {\n" +
                    "            background-color: #4CAF50;\n" +
                    "            color: #fff;\n" +
                    "            padding: 10px;\n" +
                    "            text-align: center;\n" +
                    "            border-radius: 8px 8px 0 0;\n" +
                    "        }\n" +
                    "        .content {\n" +
                    "            padding: 20px;\n" +
                    "            line-height: 1.6;\n" +
                    "        }\n" +
                    "        \n" +
                    "        }\n" +
                    "        .footer {\n" +
                    "            font-size: 12px;\n" +
                    "            color: #777;\n" +
                    "            text-align: center;\n" +
                    "            padding: 10px;\n" +
                    "            border-top: 1px solid #ddd;\n" +
                    "            margin-top: 20px;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <div class=\"header\">\n" +
                    "            <h1>¡Bienvenido a nuestra comunidad!</h1>\n" +
                    "        </div>\n" +
                    "        <div class=\"content\">\n" +
                    "            <p>Estimado usuario,</p>\n" +
                    "            <p>Gracias por activar tu cuenta con nosotros.</p>\n" +
                    "\n" +
                    "            \n" +
                    "\n" +
                    "            <p>!</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"footer\">\n" +
                    "            <p>Si tienes alguna pregunta, no dudes en contactarnos.</p>\n" +
                    "            <p>&copy; 2025 - Nuestra Empresa. Todos los derechos reservados.</p>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>\n";

            emailServicio.enviaremailHtml( new EmailDTO(" ACTIVACION CUENTA PRIMERA VEZ", cuerpo, cuentaOpt.get().getEmail()) );
        }

        cuentaRepo.save(cuenta); // Guardar el cambio en la base de datos

        return "Cuenta activada exitosamente.";
    }

   /*  @Override
    public List<Reporte> buscarReportesPorTitulo(String tituloReporte) throws Exception {
        List<Cuenta> cuentas = cuentaRepo.buscarReportesPorTitulo(tituloReporte);
    
        return cuentas.stream()
                .flatMap(cuenta -> cuenta.getReportes().stream())
                .filter(reporte -> tituloReporte.equals(reporte.getTitulo()))
                .collect(Collectors.toList());
    } */
   /*  @Override
    public List<Cuenta> buscarReportesPorTitulo(String tituloReporte) {
        return cuentaRepo.buscarReportesPorTitulo(tituloReporte);
    }
    

    public static int generarNumeroAleatorio() {
        Random random = new Random();
        return random.nextInt(10000); // Genera un número entre 0 y 9999
    }

    @Override
    public List<ItemCuentaDTO> listarCuentas() {


        //Obtenemos todas las cuentas de los usuarios de la base de datos
        List<Cuenta> cuentas = cuentaRepo.findAll();

        //Creamos una lista de DTOs
        List<ItemCuentaDTO> items = new ArrayList<>();


        //Recorremos la lista de cuentas y por cada uno creamos un DTO y lo agregamos a la lista
        for (Cuenta cuenta : cuentas) {
            items.add( new ItemCuentaDTO(
                    cuenta.getId(), 
                    cuenta.getUsuario().getNombre(), 
                    cuenta.getEmail(),
                    cuenta.getUsuario().getEmail()
                  
            ));
        }


        return items;
    }

    @Override
    public Cuenta obtenerPorEmail(String email) throws Exception {

       // System.out.println(email);

        Optional<Cuenta> cuentaOptional = cuentaRepo.findByEmail(email);

       // System.out.println(cuentaOptional.isEmpty());

        if(cuentaOptional.isEmpty()){
            throw new Exception("No existe una cuenta registrada con el email " + email + ".");
        }

        Cuenta cuenta = cuentaOptional.get();

        if(cuenta.getEstado() == EstadoUsuario.ELIMINADO){
            throw new Exception("La cuenta registrada con el email " + email + " esta ELIMINADA.");
        }

        return cuenta;

    }

    @Override
    public String enviarCodigoActivacionCuenta(String email) throws Exception {

        Cuenta cuenta = obtenerEmail(email);
        String codigoValidacion = generarCodigo();

        cuenta.setCodigoValidacionRegistro(new CodigoValidacion(
                LocalDateTime.now(),
                codigoValidacion, codigoValidacion
        ));

        cuentaRepo.save(cuenta);

        emailServicio.enviaremail( new EmailDTO("CODIGO DE ACTIVACIÓN CUENTA", codigoValidacion, email) );

        return "Se ha enviado un email con el código de activación de su cuenta";

    }

    @Override
    public void eliminarReportes(String id, List<Reporte> ReportesEliminar) throws Exception {
        // Buscar la cuenta por su ID
        Optional<Cuenta> optionalCuenta = cuentaRepo.findById(id);

        // Validar si la cuenta existe
        if (optionalCuenta.isEmpty()) {
            throw new Exception("La cuenta con ID " + id + " no existe.");
        }

        Cuenta cuenta = optionalCuenta.get();

        // Obtener la lista de boletas del cliente
        List<Reporte> reportesCliente = cuenta.getReportes();

        // Filtrar y eliminar las boletas que coinciden con la lista proporcionada
        reportesCliente.removeIf(reportessCliente ->
        ReportesEliminar.stream().anyMatch(boletaAEliminar ->
        reportessCliente.getId().equals(boletaAEliminar.getId())
                )
        );

        // Guardar la cuenta actualizada en el repositorio
        cuentaRepo.save(cuenta);
    }


    private Cuenta obtenerEmail(String email) throws Exception {

        Optional<Cuenta> cuentaOptional = cuentaRepo.buscaremail(email);

        if(cuentaOptional.isEmpty()){
            throw new Exception("El email dado no está registrado.");
        }

        Cuenta cuenta = cuentaOptional.get();

        if(cuenta.getEstado().equals(EstadoUsuario.ELIMINADO)){
            throw new Exception("La cuenta registrada con el email " + email + " esta ELIMINADA.");
        }

        return cuenta;
    }

    private Cuenta obtenerCuenta(String id) throws Exception {

        Optional<Cuenta> cuentaOptional = cuentaRepo.findById(id);

        if(cuentaOptional.isEmpty()){
            throw new Exception("No existe una cuenta registrada con el id " + id + ".");
        }

        Cuenta cuenta = cuentaOptional.get();

        if(cuenta.getEstado().equals(EstadoUsuario.ELIMINADO)){
            throw new Exception("La cuenta registrada con el email " + id + " esta ELIMINADA.");
        }

        return cuenta;
    }


    private boolean existeCuenta(String cuenta) {

        Optional<Cuenta> optionalCuenta = cuentaRepo.findById(cuenta);

        if (optionalCuenta.isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

    private boolean existeCedula(String cedula) {
        return cuentaRepo.buscarCedula(cedula).isPresent();
    }

    private boolean existeemail(String email) {

        return cuentaRepo.buscaremail(email).isPresent();

    }

    private boolean existeEmail(String email) {
        return cuentaRepo.buscaremail(email).isPresent();
    }

    private String generarCodigo() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder codigo = new StringBuilder();

        for(int i = 0; i < 6; i++){
            int indice = (int) (caracteres.length() * Math.random());
            codigo.append(caracteres.charAt(indice));
        }

        return codigo.toString();
    }

    private String encriptarPassword(String password){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode( password );
    }

    private Map<String, Object> construirClaims(Cuenta cuenta) {
        return Map.of(
                "rol", cuenta.getRol(),
                "nombre", cuenta.getUsuario().getNombre(),
                "id", cuenta.getId()
        );
    }
    private Cuenta obtenerCuentaPorIdPropietario(String idPropietario) throws Exception {
        Optional<Cuenta> cuentaOptional = cuentaRepo.findById(idPropietario);
        if (cuentaOptional.isEmpty()) {
            throw new Exception("No existe una cuenta con el propietario " + idPropietario);
        }
        return cuentaOptional.get();
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
 */
   


