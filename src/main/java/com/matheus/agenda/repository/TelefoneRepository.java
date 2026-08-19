package com.matheus.agenda.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matheus.agenda.model.Telefone;

public interface TelefoneRepository extends JpaRepository<Telefone, Long> {
}
