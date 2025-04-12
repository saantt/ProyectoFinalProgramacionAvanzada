package co.edu.uniquindio.proyectofinal.proyecto.exception;

import co.edu.uniquindio.proyectofinal.proyecto.dto.MensajeDTO;
import co.edu.uniquindio.proyectofinal.proyecto.dto.validacion.ValidacionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;


@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<MensajeDTO<String>> noResourceFoundExceptionHandler (NoResourceFoundException ex){
        return ResponseEntity.status(404).body( new MensajeDTO<>(true, "El recurso no fue encontrado") );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<MensajeDTO<String>> generalExceptionHandler (Exception e){
        return ResponseEntity.internalServerError().body( new MensajeDTO<>(true, e.getMessage()) );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MensajeDTO<List<ValidacionDTO>>> validationExceptionHandler ( MethodArgumentNotValidException ex ) {
        List<ValidacionDTO> errores = new ArrayList<>();
        BindingResult results = ex.getBindingResult();


        for (FieldError e: results.getFieldErrors()) {
            errores.add( new ValidacionDTO(e.getField(), e.getDefaultMessage()) );
        }


        return ResponseEntity.badRequest().body( new MensajeDTO<>(true, errores) );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> manejarRecursoNoEncontrado(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


}