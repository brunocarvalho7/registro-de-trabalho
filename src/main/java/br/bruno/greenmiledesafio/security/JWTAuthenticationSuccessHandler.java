package br.bruno.greenmiledesafio.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private JWTAuthenticationService tokenService;

    //TODO: SUBSTITUIR ESSE CONSTRUTOR PARA UM AUTOWIRED
    public JWTAuthenticationSuccessHandler(JWTAuthenticationService tokenService){
        this.tokenService = tokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        tokenService.addAuthentication(response, authentication);
    }
}
