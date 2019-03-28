package br.bruno.greenmiledesafio.security;

import br.bruno.greenmiledesafio.model.UsuarioDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JWTAuthenticationService tokenService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTAuthenticationService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        UsernamePasswordAuthenticationToken authRequest = null;
        try {
            UsuarioDTO usuario = new ObjectMapper().readValue(request.getInputStream(),UsuarioDTO.class);

            authRequest = new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        setDetails(request, authRequest);

        Authentication auth = this.authenticationManager.authenticate(authRequest);
        return auth;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        tokenService.addAuthentication(response, authResult);
    }
}
