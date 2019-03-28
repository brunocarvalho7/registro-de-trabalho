package br.bruno.greenmiledesafio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Permissao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    @JsonIgnore
    public String getAuthority() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
