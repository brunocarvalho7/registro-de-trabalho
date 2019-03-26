package br.bruno.greenmiledesafio.security;

import br.bruno.greenmiledesafio.model.Usuario;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static br.bruno.greenmiledesafio.security.SecurityConstants.*;

public class JWTAuthenticationService {

    private TokenHandler tokenHandler;


    public JWTAuthenticationService(UserDetailsService userDetailsService){
        this.tokenHandler = new TokenHandler(userDetailsService);
    }

    public void addAuthentication(HttpServletResponse response, Authentication authentication) throws UnsupportedEncodingException {
        Usuario usuario = (Usuario) authentication.getPrincipal();

        String header = TOKEN_PREFIX +tokenHandler.createTokenForUser(usuario);

        response.addHeader(HEADER_STRING,  header);
        response.addCookie(new  Cookie(HEADER_STRING, URLEncoder.encode(header, "UTF-8")));
    }

    public Authentication getAuthentication(String token){
        Usuario usuario = tokenHandler.parseUserFromToken(token);
        System.out.println("getAuthentication"+usuario);

        return usuario != null ? new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities()) : null;
    }

}
