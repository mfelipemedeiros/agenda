package com.matheus.agenda.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.matheus.agenda.model.Contato;
import com.matheus.agenda.model.Telefone;
import com.matheus.agenda.repository.ContatoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContatoService {

    private final ContatoRepository contatoRepository;
    private final LogService logService;

    public ContatoService(
            ContatoRepository contatoRepository,
            LogService logService
    ) {
        this.contatoRepository = contatoRepository;
        this.logService = logService;
    }

    public List<Contato> listarTodos() {
        return contatoRepository.listarTodosComTelefones();
    }

    public List<Contato> pesquisar(String tipo, String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return listarTodos();
        }

        termo = termo.trim();

        if ("nome".equalsIgnoreCase(tipo)) {
            return contatoRepository.pesquisarPorNome(termo);
        }

        if ("telefone".equalsIgnoreCase(tipo)) {
            return contatoRepository.pesquisarPorTelefone(termo);
        }

        return contatoRepository.pesquisarPorNomeOuTelefone(termo);
    }

    public Optional<Contato> buscarPorId(Long id) {
        return contatoRepository.findById(id);
    }

    @Transactional
    public Contato salvar(Contato contato) {
        List<Telefone> telefonesValidos = new ArrayList<>();

        if (contato.getTelefones() != null) {
            for (Telefone telefone : contato.getTelefones()) {
                if (telefone.getNumero() != null && !telefone.getNumero().trim().isEmpty()) {
                    telefone.setNumero(telefone.getNumero().trim());
                    telefone.setContato(contato);
                    telefonesValidos.add(telefone);
                }
            }
        }

        contato.getTelefones().clear();

        for (Telefone telefone : telefonesValidos) {
            contato.adicionarTelefone(telefone);
        }

        return contatoRepository.save(contato);
    }

    @Transactional
    public void excluir(Long id) {
        Contato contato = contatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado"));

        contato.getTelefones().size();

        logService.registrarExclusao(contato);

        contatoRepository.delete(contato);
    }
}