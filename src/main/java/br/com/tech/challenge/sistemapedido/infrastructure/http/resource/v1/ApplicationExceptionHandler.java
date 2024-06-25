package br.com.tech.challenge.sistemapedido.infrastructure.http.resource.v1;

import br.com.tech.challenge.sistemapedido.application.dto.InputErrorDTO;
import br.com.tech.challenge.sistemapedido.domain.exception.*;
import br.com.tech.challenge.sistemapedido.infrastructure.exception.DefaultFeignException;
import br.com.tech.challenge.sistemapedido.infrastructure.integration.transfer.IntegrationErrorTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<InputErrorDTO> constraintViolation(ConstraintViolationException exception) {
        return new ResponseEntity<>(new InputErrorDTO(exception.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<InputErrorDTO> constraintViolation(EntityNotFoundException exception) {
        return new ResponseEntity<>(new InputErrorDTO(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({PedidoJaPagoException.class,
            PedidoNaoPagoException.class,
            PedidoStatusIncorretoException.class,
            PedidoCanceladoException.class})
    public ResponseEntity<InputErrorDTO> pedidoJaPago(RuntimeException exception) {
        return new ResponseEntity<>(new InputErrorDTO(exception.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<InputErrorDTO> constraintViolation(UsernameNotFoundException exception) {
        return new ResponseEntity<>(new InputErrorDTO(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DefaultFeignException.class)
    public ResponseEntity<IntegrationErrorTO> defaultFeign(DefaultFeignException exception) {
        return new ResponseEntity<>(new IntegrationErrorTO(exception.getStatus(), exception.getError(), exception.getMessage()),
                HttpStatus.valueOf(exception.getStatus()));
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<InputErrorDTO> constraintViolation(InternalErrorException exception) {
        return new ResponseEntity<>(new InputErrorDTO(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<InputErrorDTO> handleAuthentication() {

        return new ResponseEntity<>(new InputErrorDTO("Credenciais inválidas"), HttpStatus.UNAUTHORIZED);
    }
}
