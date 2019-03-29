package br.bruno.greenmiledesafio.controller;

import br.bruno.greenmiledesafio.exception.ExceptionResponse;
import br.bruno.greenmiledesafio.model.LoginDTO;
import br.bruno.greenmiledesafio.security.CustomUserDetailsService;
import br.bruno.greenmiledesafio.security.JWTAuthenticationService;
import br.bruno.greenmiledesafio.security.SecurityConstants;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("v1/login")
@Api(value = "Login", description = "REST API para realizar o login", tags = {"Login"})
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("")
    @ApiOperation(value = "Efetua login no sistema", response = String.class, code = 200)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = ExceptionResponse.class,
                    responseHeaders = {@ResponseHeader(name = "Authorization",
                            description = "Token com a autorização do usuário", response = String.class)}),
            @ApiResponse(code = 400, message = "Credenciais inválidas", response = ExceptionResponse.class),
    })
    public ResponseEntity<String> signIn(@ApiParam(name="Dados do login", value="Credencias do usuario a ser autenticado",
            required = true) @RequestBody LoginDTO loginDTO,
        HttpServletResponse response){

        try {
            JWTAuthenticationService jwtAuthenticationService = new JWTAuthenticationService(new CustomUserDetailsService());

            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                                            (loginDTO.getUsername(), loginDTO.getPassword()));

            jwtAuthenticationService.addAuthentication(response, auth);

            return ResponseEntity.ok(response.getHeader(SecurityConstants.HEADER_STRING));
        } catch (AuthenticationException | IOException e) {
            throw new BadCredentialsException("Credenciais inválidas");
        }
    }

}
