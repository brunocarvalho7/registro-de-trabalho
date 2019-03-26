package br.bruno.greenmiledesafio.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
public class Permissao implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String descricao;

    public Permissao(){
    }

    public Permissao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String getAuthority() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
