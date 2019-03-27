package br.bruno.greenmiledesafio.security;

public class SecurityConstants {

    private SecurityConstants(){
        throw new IllegalArgumentException("Classe utilitária");
    }

    public static final String SECRET = "S3CR3T";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final long EXPIRATION_TIME = 86400000; //1 dia = 86400000 microsegundos
}
