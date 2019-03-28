package br.bruno.greenmiledesafio.security;

import br.bruno.greenmiledesafio.model.Usuario;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;

import static br.bruno.greenmiledesafio.security.SecurityConstants.*;

public class JWTAuthenticationService {

    private TokenHandler tokenHandler;

    public JWTAuthenticationService(UserDetailsService userDetailsService) throws IOException {
        this.tokenHandler = new TokenHandler(userDetailsService);
    }

    public void addAuthentication(HttpServletResponse response, Authentication authentication) throws IOException {
        Usuario usuario = (Usuario) authentication.getPrincipal();

        String header = TOKEN_PREFIX +tokenHandler.createTokenForUser(usuario);

        response.getWriter().write(header);
        response.addHeader(HEADER_STRING,  header);
        response.addCookie(new  Cookie(HEADER_STRING, URLEncoder.encode(header, "UTF-8")));
    }

    public Authentication getAuthentication(String token){
        Usuario usuario = tokenHandler.parseUserFromToken(token);

        return usuario != null ? new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()) : null;
    }

}
