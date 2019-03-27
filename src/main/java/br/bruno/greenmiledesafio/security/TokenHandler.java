package br.bruno.greenmiledesafio.security;

import br.bruno.greenmiledesafio.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;

import static br.bruno.greenmiledesafio.security.SecurityConstants.EXPIRATION_TIME;
import static br.bruno.greenmiledesafio.security.SecurityConstants.SECRET;

public class TokenHandler {

    private final UserDetailsService userService;

    public TokenHandler(UserDetailsService userService) {
        this.userService = userService;
    }

    public Usuario parseUserFromToken(String token){
        String username = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token)
                        .getBody().getSubject();

        return (Usuario) userService.loadUserByUsername(username);
    }

    public String createTokenForUser(Usuario usuario){
        return Jwts.builder()
                .setSubject(usuario.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}
