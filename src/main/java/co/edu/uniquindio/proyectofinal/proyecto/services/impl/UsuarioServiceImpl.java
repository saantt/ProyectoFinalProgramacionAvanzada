package co.edu.uniquindio.proyectofinal.proyecto.services.impl;

import co.edu.uniquindio.proyectofinal.proyecto.dto.email.EmailDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.reporte.InfoReporteDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.ActivarCuentaDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.CambiarPasswordDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.CrearUsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.EditarUsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.UsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.validacion.EnviarCodigoDTO;
import co.edu.uniquindio.proyectofinal.proyecto.exception.ResourceNotFoundException;
import co.edu.uniquindio.proyectofinal.proyecto.model.CodigoValidacion;
import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoUsuario;
import co.edu.uniquindio.proyectofinal.proyecto.repository.UsuarioRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.EmailService;
import co.edu.uniquindio.proyectofinal.proyecto.services.UsuarioService;
import co.edu.uniquindio.proyectofinal.proyecto.util.UsuarioMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.mongodb.core.query.Query;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;
    private final MongoTemplate mongoTemplate;
    private final EmailService emailServicio;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public void crear(CrearUsuarioDTO crearUsuarioDTO) throws Exception {
        if (existeEmail(crearUsuarioDTO.email())) {
            throw new Exception("El correo " + crearUsuarioDTO.email() + " ya está en uso");
        }

        Usuario usuario = usuarioMapper.toDocument(crearUsuarioDTO);
        usuario.setPassword(passwordEncoder.encode(crearUsuarioDTO.password()));

        // Usa el builder correctamente
        CodigoValidacion codigoValidacion = CodigoValidacion.builder()
                .codigo(generarCodigo())
                .fechaCreacion(LocalDateTime.now())
                .email(usuario.getEmail()) // Campo requerido
                .build();

        usuario.setCodigoValidacion(codigoValidacion);
        usuarioRepositorio.save(usuario);
        String asunto = "Verificación de cuenta";
        String destinatario = usuario.getEmail(); // Primero declaras el destinatario

        String cuerpo = """
                <html>
                    <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                        <div style="max-width: 600px; margin: auto; background-color: white; padding: 20px; border-radius: 8px;">
                            <h2 style="color: #4CAF50; text-align: center;">Verificación de cuenta</h2>
                            <p>¡Hola <strong>%s</strong>!</p>
                            <p>Tu código de verificación es:</p>
                            <h1 style="background-color: #eee; padding: 10px; border-radius: 4px; text-align: center;">%s</h1>
                            <p>Si no solicitaste este código, por favor ignora este correo.</p>
                            <p style="font-size: 12px; color: #888;">Este es un correo automático, por favor no respondas.</p>
                        </div>
                    </body>
                </html>
                """
                .formatted(usuario.getNombre(), codigoValidacion);
        emailServicio.enviarCorreo(new EmailDTO(asunto, cuerpo, destinatario));

    }

    @Override
    public void editar(String id, EditarUsuarioDTO editarUsuarioDTO) throws Exception {
        Usuario usuario = obtenerUsuarioPorId(id);
        usuarioMapper.editarUsuarioDTO(editarUsuarioDTO, usuario);
        usuarioRepositorio.save(usuario);
    }

    @Override
    public void eliminar(String id) throws Exception {
        if (!ObjectId.isValid(id)) {
            throw new Exception("ID inválido: " + id);
        }
        Usuario usuario = obtenerUsuarioPorId(id);
        usuario.setEstado(EstadoUsuario.ELIMINADO);
        usuarioRepositorio.save(usuario);
    }

    @Override
    public UsuarioDTO obtener(String id) throws Exception {
        if (!ObjectId.isValid(id)) {
            throw new Exception("ID inválido: " + id);
        }
        Usuario usuario = obtenerUsuarioPorId(id);
        return usuarioMapper.toDTO(usuario);
    }

    @Override
    public List<UsuarioDTO> listarTodos(String nombre, String ciudad, int pagina) {
        if (pagina < 0)
            throw new RuntimeException("La página no puede ser menor a 0");
        // Crear criterios dinámicos
        Criteria criteria = new Criteria();
        if (nombre != null && !nombre.isEmpty()) {
            criteria.and("nombre").regex(nombre, "i"); // Ignora a mayúsculas/minúsculas
        }
        if (ciudad != null && !ciudad.isEmpty()) {
            criteria.and("ciudad").regex(ciudad, "i");
        }
        // Crear la consulta con los criterios y la paginación de 5 elementos por página
        Query query = new Query().addCriteria(criteria).with(PageRequest.of(pagina, 5));
        List<Usuario> usuarios = mongoTemplate.find(query, Usuario.class);
        // Convertir la lista de usuarios a una lista de DTOs
        return usuarios.stream()
                .map(usuarioMapper::toDTO)
                .toList();
    }

    @Override
    public List<InfoReporteDTO> obtenerReportesUsuario(String id) {

        return null;
    }

    private boolean existeEmail(String email) {
        return usuarioRepositorio.findByEmail(email).isPresent();
    }

    // Metodo utilizado en crear, editar y eliminar (Metodo de apoyo para no repetir
    // codigo)
    private Usuario obtenerUsuarioPorId(String id) throws Exception {
        if (!ObjectId.isValid(id)) {
            throw new ResourceNotFoundException("El ID proporcionado no es válido: " + id);
        }
        ObjectId objectId = new ObjectId(id);
        return usuarioRepositorio.findById(objectId)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario con el id " + id));
    }

    // Metodo usado en activarCuenta y cambiarPassword para obtener el usuario por
    // email
    private Usuario obtenerPorEmail(String email) throws ResourceNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByEmail(email);
        if (usuarioOptional.isEmpty()) {
            throw new ResourceNotFoundException("No se encontró el usuario con el email " + email);
        }
        return usuarioOptional.get();
    }

    private String generarCodigo() {
        String digitos = "0123456789";
        StringBuilder codigo = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int indice = (int) (Math.random() * digitos.length());
            codigo.append(digitos.charAt(indice));
        }
        return codigo.toString();
    }

    // Envia el codigo para cambiar password
    @Override
    public void enviarCodigoVerificacion(EnviarCodigoDTO enviarCodigoDTO) throws Exception {
        Usuario usuario = obtenerPorEmail(enviarCodigoDTO.email());

        CodigoValidacion codigoValidacion = CodigoValidacion.builder()
                .codigo(generarCodigo())
                .fechaCreacion(LocalDateTime.now())
                .email(usuario.getEmail()) // Campo requerido
                .build();

        usuario.setCodigoValidacion(codigoValidacion);
        usuarioRepositorio.save(usuario);
        String asunto = "Restablecer Password";
        String cuerpo = "¡Hola " + usuario.getNombre() + "! Tu código para cambiar password es: " + codigoValidacion;
        String destinatario = usuario.getEmail();
        emailServicio.enviarCorreo(new EmailDTO(asunto, cuerpo, destinatario));
    }

    @Override
    public void cambiarPassword(CambiarPasswordDTO cambiarPasswordDTO) throws Exception {
        Usuario usuario = obtenerPorEmail(cambiarPasswordDTO.email());
        if (!usuario.getCodigoValidacion().getCodigo().equals(cambiarPasswordDTO.codigoValidacion())) {
            throw new Exception("El código de verificación es incorrecto");
        }
        if (usuario.getCodigoValidacion() == null) {
            throw new Exception("No usuario no tiene un código de verificación");
        }
        if (!LocalDateTime.now().isBefore(usuario.getCodigoValidacion().getFechaCreacion().plusMinutes(15))) {
            throw new Exception("El código de verificación ha caducado");
        }
        usuario.setPassword(passwordEncoder.encode(cambiarPasswordDTO.nuevaPassword()));
        usuario.setCodigoValidacion(null);
        usuarioRepositorio.save(usuario);
    }

    @Override
    public void activarCuenta(ActivarCuentaDTO activarCuentaDTO) throws Exception {
        Usuario usuario = obtenerPorEmail(activarCuentaDTO.email());
        if (!usuario.getCodigoValidacion().getCodigo().equals(activarCuentaDTO.codigoValidacion())) {
            throw new Exception("El código de verificación es incorrecto");
        }
        if (!LocalDateTime.now().isBefore(usuario.getCodigoValidacion().getFechaCreacion().plusMinutes(15))) {
            throw new Exception("El código de verificación ha caducado");
        }
        if (usuario.getCodigoValidacion() == null) {
            throw new Exception("No se encontró el usuario con el email ");
        }
        usuario.setEstado(EstadoUsuario.ACTIVO);
        usuario.setCodigoValidacion(null);
        usuarioRepositorio.save(usuario);
    }

}
