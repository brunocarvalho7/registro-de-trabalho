package br.bruno.greenmiledesafio.controller;

import br.bruno.greenmiledesafio.exception.DadosInvalidosException;
import br.bruno.greenmiledesafio.exception.ExceptionResponse;
import br.bruno.greenmiledesafio.exception.LoginExistenteException;
import br.bruno.greenmiledesafio.exception.UsuarioNaoEncontradoException;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.io.IOException;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidDefinitionException.class,
                       UsuarioNaoEncontradoException.class,
                       ConstraintViolationException.class,
                       LoginExistenteException.class,
                       DadosInvalidosException.class,
                       IOException.class})
    public ResponseEntity<ExceptionResponse> badRequest(Exception ex) {
        ExceptionResponse response = ExceptionResponse.Builder.anExceptionResponseBuilder()
                .withStatus(HttpStatus.BAD_REQUEST)
                .withError(ex.getClass().getSimpleName())
                .withMessage(ex.getMessage())
                .build();

        return ResponseEntity.badRequest().body(response);
    }
}
