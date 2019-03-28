package br.bruno.greenmiledesafio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class UsuarioDTO {

    @NotNull
    private String nome;
    @NotNull
    private String username;
    @NotNull
    private String password;

    private ArrayList<String> permissoes;

    public UsuarioDTO() {
    }

    public UsuarioDTO( String username, String password) {
        this.nome = "Default";
        this.username = username;
        this.password = password;
        this.permissoes = new ArrayList<>();
    }

    public UsuarioDTO(String nome,String username, String password, ArrayList<String> permissoes) {
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

    public ArrayList<String> getPermissoes() {
        return permissoes;
    }

    @JsonIgnore
    public boolean isValid(){
        if(this.nome != null && this.nome.trim().length() > 0)
            if(this.username != null && this.username.trim().length() > 0)
                if(this.password != null && this.password.trim().length() > 0)
                    return true;

        return false;
    }
}
