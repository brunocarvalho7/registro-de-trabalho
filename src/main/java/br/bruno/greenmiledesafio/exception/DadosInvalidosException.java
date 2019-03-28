package br.bruno.greenmiledesafio.exception;

public class DadosInvalidosException extends Exception {

    public DadosInvalidosException() {
        super("Os dados informados estão incompletos!");
    }
}
