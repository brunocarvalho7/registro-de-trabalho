package br.bruno.greenmiledesafio.exception;

public class UsuarioNaoEncontradoException extends Exception {

    public UsuarioNaoEncontradoException(Long id){
        super(String.format("Usuário com código %d não foi localizado", id));
    }
}
