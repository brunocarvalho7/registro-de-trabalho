package br.bruno.greenmiledesafio.exception;

public class HorasTrabalhadasNaoEncontradaException extends Exception {

    public HorasTrabalhadasNaoEncontradaException(Long id){
        super(String.format("Registro com código %d não foi localizado", id));
    }
}
