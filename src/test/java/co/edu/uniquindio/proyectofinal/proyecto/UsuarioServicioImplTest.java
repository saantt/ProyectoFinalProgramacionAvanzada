package co.edu.uniquindio.proyectofinal.proyecto;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import co.edu.uniquindio.proyectofinal.proyecto.services.EmailService;
import co.edu.uniquindio.proyectofinal.proyecto.dto.email.EmailDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.CrearUsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.EditarUsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.usuario.UsuarioDTO;
import co.edu.uniquindio.proyectofinal.proyecto.exception.ResourceNotFoundException;
import co.edu.uniquindio.proyectofinal.proyecto.model.CodigoValidacion;
import co.edu.uniquindio.proyectofinal.proyecto.model.Usuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.EstadoUsuario;
import co.edu.uniquindio.proyectofinal.proyecto.model.enums.Rol;
import co.edu.uniquindio.proyectofinal.proyecto.repository.UsuarioRepository;
import co.edu.uniquindio.proyectofinal.proyecto.services.EmailService;
import co.edu.uniquindio.proyectofinal.proyecto.services.impl.UsuarioServiceImpl;
import co.edu.uniquindio.proyectofinal.proyecto.util.UsuarioMapper;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

class UsuarioServicioImplTest {

        @InjectMocks
        private UsuarioServiceImpl usuarioServicio;

        @Mock
        private UsuarioRepository usuarioRepositorio;

        @Mock
        private UsuarioMapper usuarioMapper;

        @Mock
        private PasswordEncoder passwordEncoder;

        private Validator validator;

        @Mock
        private EmailService emailServicio;

        @BeforeEach
        void setUp() {
                // Inicializa mocks de Mockito
                MockitoAnnotations.openMocks(this);
                validator = Validation.buildDefaultValidatorFactory().getValidator();

                when(passwordEncoder.encode(anyString())).thenReturn("passwordEncriptada");
        }

        // Test de crear
        @Test
        void crearUsuarioExitosamente() throws Exception {
                // 1. Configuración
                CrearUsuarioDTO dto = new CrearUsuarioDTO(
                                "Juan Pérez",
                                "juan@test.com",
                                "password123",
                                "3110000000",
                                "Armenia");

                // 2. Mocks
                Usuario usuarioBase = new Usuario();
                usuarioBase.setEmail(dto.email());

                Usuario usuarioGuardado = new Usuario();
                usuarioGuardado.setEmail(dto.email());
                usuarioGuardado.setCodigoValidacion(new CodigoValidacion("12345", LocalDateTime.now()));
                usuarioGuardado.setPassword("encrypted123");

                // 3. Configurar mocks
                when(usuarioRepositorio.findByEmail(dto.email())).thenReturn(Optional.empty());
                when(usuarioMapper.toDocument(dto)).thenReturn(usuarioBase);
                when(passwordEncoder.encode(dto.password())).thenReturn("encrypted123");
                when(usuarioRepositorio.save(any(Usuario.class))).thenReturn(usuarioGuardado);
                doNothing().when(emailServicio).enviarCorreo(any(EmailDTO.class));

                // 4. Ejecución
                usuarioServicio.crear(dto);

                // 5. Verificaciones
                ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
                verify(usuarioRepositorio).save(captor.capture());

                Usuario usuarioParaGuardar = captor.getValue();

                // Verifica los campos críticos
                assertNotNull(usuarioParaGuardar.getCodigoValidacion(), "El código de validación no debe ser nulo");
                assertNotNull(usuarioParaGuardar.getCodigoValidacion().getCodigo(),
                                "El código generado no debe ser nulo");
                assertNotNull(usuarioParaGuardar.getCodigoValidacion().getFechaCreacion(),
                                "La fecha de creación no debe ser nula");
                assertEquals("encrypted123", usuarioParaGuardar.getPassword(), "La contraseña debe estar encriptada");

                // Verifica interacciones
                verify(emailServicio).enviarCorreo(any(EmailDTO.class));
                verify(passwordEncoder).encode(dto.password());
        }

        @Test
        void crearUsuarioConEmailDuplicadoDebeLanzarExcepcion() {
                // Arrange (Preparación de datos y mocks)
                CrearUsuarioDTO crearUsuarioDTO = new CrearUsuarioDTO(
                                "Ana López",
                                "ana@example.com",
                                "password123",
                                "3200000000",
                                "Bogotá");
                Usuario usuarioExistente = new Usuario();
                when(usuarioRepositorio.findByEmail(crearUsuarioDTO.email()))
                                .thenReturn(Optional.of(usuarioExistente));
                // Act (Ejecución)
                Exception exception = assertThrows(Exception.class, () -> usuarioServicio.crear(crearUsuarioDTO));
                // Assert (Verificación)
                assertEquals("El correo ana@example.com ya está en uso", exception.getMessage());
                verify(usuarioRepositorio, never()).save(any(Usuario.class));
        }

        @Test
        void crearUsuarioConDatosInvalidosDebeFallar() {
                // Arrange
                CrearUsuarioDTO crearUsuarioDTO = new CrearUsuarioDTO(
                                "", // Nombre vacío
                                "correo-no-valido", // Email mal formado
                                "123", // Contraseña demasiado corta
                                "", // Teléfono vacío
                                "" // Ciudad vacía
                );
                // Act
                var violaciones = validator.validate(crearUsuarioDTO);
                // Assert
                assertFalse(violaciones.isEmpty(), "Se esperaban violaciones de validación");
                assertEquals(5, violaciones.size()); // Opcional: válida que haya 5 errores exactamente
        }

        // Test de editar
        @Test
        void editarUsuarioExitosamente() throws Exception {
                // Arrange
                String id = new ObjectId().toString();
                EditarUsuarioDTO editarUsuarioDTO = new EditarUsuarioDTO(
                                "Kevin Actualizado",
                                "3100000000",
                                "Armenia");
                Usuario usuarioExistente = new Usuario();
                usuarioExistente.setId(new ObjectId(id));
                usuarioExistente.setNombre("Kevin Original");
                usuarioExistente.setTelefono("3200000000");
                usuarioExistente.setCiudad("Bogotá");

                when(usuarioRepositorio.findById(new ObjectId(id))).thenReturn(Optional.of(usuarioExistente));
                // Act
                usuarioServicio.editar(id, editarUsuarioDTO);
                // Assert
                verify(usuarioMapper).editarUsuarioDTO(editarUsuarioDTO, usuarioExistente);
                verify(usuarioRepositorio).save(usuarioExistente);
        }

        @Test
        void editarUsuarioConIdInvalidoDebeLanzarExcepcion() {
                // Arrange
                String idInvalido = "1234"; // No es un ObjectId válido
                EditarUsuarioDTO editarUsuarioDTO = new EditarUsuarioDTO(
                                "Kevin Actualizado",
                                "3100000000",
                                "Cali");
                // Act & Assert
                Exception exception = assertThrows(ResourceNotFoundException.class,
                                () -> usuarioServicio.editar(idInvalido, editarUsuarioDTO));
                assertEquals("El ID proporcionado no es válido: " + idInvalido, exception.getMessage());
                // Verificamos que no se haya llamado al repositorio ni al mapper
                verify(usuarioRepositorio, never()).findById(any());
                verify(usuarioMapper, never()).editarUsuarioDTO(any(), any());
                verify(usuarioRepositorio, never()).save(any());
        }

        @Test
        void editarUsuarioNoExistenteDebeLanzarExcepcion() {
                // Arrange
                String idValido = new ObjectId().toString();
                EditarUsuarioDTO editarUsuarioDTO = new EditarUsuarioDTO(
                                "Kevin Actualizado",
                                "3100000000",
                                "Cali");
                // Configuración del mock: el repositorio no encuentra al usuario
                when(usuarioRepositorio.findById(new ObjectId(idValido))).thenReturn(Optional.empty());
                // Act & Assert
                Exception exception = assertThrows(ResourceNotFoundException.class,
                                () -> usuarioServicio.editar(idValido, editarUsuarioDTO));
                assertEquals("No se encontró el usuario con el id " + idValido, exception.getMessage());
                // Verificamos que no se haya llamado al mapper ni a save
                verify(usuarioMapper, never()).editarUsuarioDTO(any(), any());
                verify(usuarioRepositorio, never()).save(any());
        }

        // Teste de eliminar
        @Test
        void eliminarUsuarioExitosamente() throws Exception {
                // Arrange
                String idValido = new ObjectId().toString();

                Usuario usuarioExistente = new Usuario();
                usuarioExistente.setId(new ObjectId(idValido));
                usuarioExistente.setEstado(EstadoUsuario.ACTIVO); // Estado inicial
                // Mock: repositorio encuentra al usuario
                when(usuarioRepositorio.findById(new ObjectId(idValido)))
                                .thenReturn(Optional.of(usuarioExistente));
                // Act
                usuarioServicio.eliminar(idValido);
                // Assert
                assertEquals(EstadoUsuario.ELIMINADO, usuarioExistente.getEstado());
                verify(usuarioRepositorio).save(usuarioExistente);
        }

        @Test
        void eliminarUsuarioConIdInvalidoDebeLanzarExcepcion() {
                // Arrange
                String idInvalido = "1234"; // No es un ObjectId válido¿
                // Act & Assert
                Exception exception = assertThrows(Exception.class, () -> usuarioServicio.eliminar(idInvalido));
                assertEquals("ID inválido: " + idInvalido, exception.getMessage());
                // Verificamos que no se haya interactuado con el repositorio
                verify(usuarioRepositorio, never()).findById(any());
                verify(usuarioRepositorio, never()).save(any());
        }

        @Test
        void eliminarUsuarioNoExistenteDebeLanzarExcepcion() {
                // Arrange
                String idValido = new ObjectId().toString();
                // Mock: el repositorio no encuentra al usuario
                when(usuarioRepositorio.findById(new ObjectId(idValido)))
                                .thenReturn(Optional.empty());
                // Act & Assert
                Exception exception = assertThrows(ResourceNotFoundException.class,
                                () -> usuarioServicio.eliminar(idValido));
                assertEquals("No se encontró el usuario con el id " + idValido, exception.getMessage());
                // Verificamos que no se intente guardar nada
                verify(usuarioRepositorio, never()).save(any());
        }

        @Test
        void obtenerUsuarioExitosamente() throws Exception {
                // Arrange
                String idValido = new ObjectId().toString();
                Usuario usuarioExistente = new Usuario();
                usuarioExistente.setId(new ObjectId(idValido));
                usuarioExistente.setNombre("Kevin");
                usuarioExistente.setEmail("kevin@example.com");
                usuarioExistente.setTelefono("3100000000");
                usuarioExistente.setCiudad("Cali");
                usuarioExistente.setEstado(EstadoUsuario.ACTIVO);
                usuarioExistente.setRol(Rol.CIUDADANO); // 👈
                usuarioExistente.setFechaRegistro(String.valueOf(LocalDateTime.now()));
                UsuarioDTO usuarioDTO = new UsuarioDTO(
                                idValido,
                                "Kevin",
                                "kevin@example.com",
                                "3100000000",
                                "Cali",
                                EstadoUsuario.ACTIVO.name(),
                                "CLIENTE",
                                usuarioExistente.getFechaRegistro().toString());
                // Mock repositorio devuelve el usuario
                when(usuarioRepositorio.findById(new ObjectId(idValido)))
                                .thenReturn(Optional.of(usuarioExistente));
                // Mock mapper convierte la entidad a DTO
                when(usuarioMapper.toDTO(usuarioExistente)).thenReturn(usuarioDTO);
                // Act
                UsuarioDTO resultado = usuarioServicio.obtener(idValido);
                // Assert
                assertNotNull(resultado);
                assertEquals(usuarioDTO, resultado);
                // Verificamos que se haya llamado al mapper
                verify(usuarioMapper).toDTO(usuarioExistente);
        }

        @Test
        void obtenerUsuarioConIdInvalidoDebeLanzarExcepcion() {
                // Arrange
                String idInvalido = "1234"; // No es un ObjectId válido
                // Act & Assert
                Exception exception = assertThrows(Exception.class, () -> usuarioServicio.obtener(idInvalido));
                assertEquals("ID inválido: " + idInvalido, exception.getMessage());
                // Verificamos que no se haya llamado al repositorio ni al mapper
                verify(usuarioRepositorio, never()).findById(any());
                verify(usuarioMapper, never()).toDTO(any());
        }

        @Test
        void obtenerUsuarioNoExistenteDebeLanzarExcepcion() {
                // Arrange
                String idValido = new ObjectId().toString();

                when(usuarioRepositorio.findById(new ObjectId(idValido)))
                                .thenReturn(Optional.empty());

                Exception exception = assertThrows(ResourceNotFoundException.class,
                                () -> usuarioServicio.obtener(idValido));
                assertEquals("No se encontró el usuario con el id " + idValido, exception.getMessage());

                verify(usuarioMapper, never()).toDTO(any());
        }

}