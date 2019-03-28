package br.bruno.greenmiledesafio.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class HorasTrabalhadas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate data;

    @NotNull
    private LocalTime qtdHoras;

    @ManyToOne
    private Usuario usuario;

    public HorasTrabalhadas() {
    }

    public HorasTrabalhadas(@NotNull LocalDate data, @NotNull LocalTime qtdHoras, Usuario usuario) {
        this.data = data;
        this.qtdHoras = qtdHoras;
        this.usuario = usuario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getQtdHoras() {
        return qtdHoras;
    }

    public void setQtdHoras(LocalTime qtdHoras) {
        this.qtdHoras = qtdHoras;
    }

    public String getUsuario() {
        return usuario != null ? usuario.getNome() : "";
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}