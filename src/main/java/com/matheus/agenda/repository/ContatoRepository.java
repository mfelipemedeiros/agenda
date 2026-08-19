package com.matheus.agenda.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.matheus.agenda.model.Contato;

import java.util.List;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

    @Query("""
           SELECT DISTINCT c
           FROM Contato c
           LEFT JOIN FETCH c.telefones t
           WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :nome, '%'))
           """)
    List<Contato> pesquisarPorNome(String nome);

    @Query("""
           SELECT DISTINCT c
           FROM Contato c
           LEFT JOIN FETCH c.telefones t
           WHERE t.numero LIKE CONCAT('%', :numero, '%')
           """)
    List<Contato> pesquisarPorTelefone(String numero);

    @Query("""
           SELECT DISTINCT c
           FROM Contato c
           LEFT JOIN FETCH c.telefones t
           WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :termo, '%'))
              OR t.numero LIKE CONCAT('%', :termo, '%')
           """)
    List<Contato> pesquisarPorNomeOuTelefone(String termo);

    @Query("""
           SELECT DISTINCT c
           FROM Contato c
           LEFT JOIN FETCH c.telefones
           ORDER BY c.nome
           """)
    List<Contato> listarTodosComTelefones();
}
