package com.matheus.agenda.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contato")
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @NotNull(message = "A idade é obrigatória")
    @Min(value = 0, message = "A idade não pode ser negativa")
    @Max(value = 999, message = "A idade deve ter no máximo 3 dígitos")
    @Column(name = "idade", nullable = false)
    private Integer idade;

    @OneToMany(
            mappedBy = "contato",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<Telefone> telefones = new ArrayList<>();

    public Contato() {
    }

    public Contato(Long id, String nome, Integer idade) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
    }

    public void adicionarTelefone(Telefone telefone) {
        telefone.setContato(this);
        this.telefones.add(telefone);
    }

    public void removerTelefone(Telefone telefone) {
        telefone.setContato(null);
        this.telefones.remove(telefone);
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones.clear();

        if (telefones != null) {
            for (Telefone telefone : telefones) {
                adicionarTelefone(telefone);
            }
        }
    }
}
