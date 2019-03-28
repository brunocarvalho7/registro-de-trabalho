package br.bruno.greenmiledesafio.exception;

public class LoginExistenteException extends Exception {

    public LoginExistenteException(String login){
        super(String.format("O login %s já está sendo utilizado!", login));
    }
}
