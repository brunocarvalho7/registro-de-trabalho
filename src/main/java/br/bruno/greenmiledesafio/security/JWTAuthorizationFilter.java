package br.bruno.greenmiledesafio.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static br.bruno.greenmiledesafio.security.SecurityConstants.HEADER_STRING;
import static br.bruno.greenmiledesafio.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JWTAuthenticationService jwtAuthenticationService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  JWTAuthenticationService service){
        super(authenticationManager);
        this.jwtAuthenticationService = service;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String header = getTokenFromCookies(request.getCookies());

        if(header == null)
            header = request.getHeader(HEADER_STRING);

        if(header == null){
            chain.doFilter(request,response);
            return;
        }

        String token = header.replace(TOKEN_PREFIX,"");
        Authentication authentication = jwtAuthenticationService.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request,response);
    }

    private String getTokenFromCookies(Cookie[] cookies) throws UnsupportedEncodingException {
        if(cookies != null)
            for(Cookie c: cookies){
                if(c.getName().equals(HEADER_STRING)){
                    String value = URLDecoder.decode(c.getValue(), "UTF-8");

                    return value.replace(TOKEN_PREFIX,"");
                }
            }

        return null;
    }
}
