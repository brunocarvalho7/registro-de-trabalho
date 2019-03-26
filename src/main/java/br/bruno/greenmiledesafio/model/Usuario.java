package br.bruno.greenmiledesafio.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String login;
    private String senha;
    private boolean ativo;
    @ManyToMany(fetch = FetchType.EAGER) //TODO: VERIFICAR ESSA QUESTAO DO LAZY
    private Collection<Permissao> permissoes = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String nome, String login, String senha, Collection<Permissao> permissoes) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.permissoes = permissoes;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissoes;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setEnabled(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean isEnabled() {
        return ativo;
    }

    @Override
    public String toString() {
        return this.login;
    }
}
