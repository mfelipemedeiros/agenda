package com.matheus.agenda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "telefone")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O número do telefone é obrigatório")
    @Size(max = 16, message = "O telefone deve ter no máximo 16 caracteres")
    @Column(name = "numero", length = 16, nullable = false)
    private String numero;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idcontato", nullable = false)
    private Contato contato;

    public Telefone() {
    }

    public Telefone(String numero) {
        this.numero = numero;
    }

    public Telefone(Long id, String numero) {
        this.id = id;
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }

    public String getNumero() {
        return numero;
    }

    public Contato getContato() {
        return contato;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }
}

