package br.bruno.greenmiledesafio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDTO {

    @NotNull
    private String nome;
    @NotNull
    private String username;
    @NotNull
    private String password;

    private List<String> permissoes;

    public UsuarioDTO() {
    }

    public UsuarioDTO( String username, String password) {
        this.nome = "Default";
        this.username = username;
        this.password = password;
        this.permissoes = new ArrayList<>();
    }

    public UsuarioDTO(String nome,String username, String password, List<String> permissoes) {
        this.nome = nome;
        this.username = username;
        this.password = password;
        this.permissoes = permissoes;
    }

    public String getNome() {
        return nome;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<String> getPermissoes() {
        return permissoes;
    }

    @JsonIgnore
    public boolean isValid(){
        if(isAFieldValid(nome) && isAFieldValid(username) && isAFieldValid(password))
            return true;

        return false;
    }

    @JsonIgnore
    public boolean isAFieldValid(String field){
        if(field != null && field.trim().length() > 0)
            return true;

        return false;
    }
}
