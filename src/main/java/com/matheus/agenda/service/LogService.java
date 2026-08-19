package com.matheus.agenda.service;


import org.springframework.stereotype.Service;

import com.matheus.agenda.model.Contato;
import com.matheus.agenda.model.Telefone;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Service
public class LogService {

    private static final String ARQUIVO_LOG = "log_exclusao_contatos.txt";

    public void registrarExclusao(Contato contato) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        String telefones = contato.getTelefones()
                .stream()
                .map(Telefone::getNumero)
                .collect(Collectors.joining(", "));

        String linha = String.format(
                "[%s] Contato excluído - ID: %d | Nome: %s | Idade: %d | Telefones: %s",
                LocalDateTime.now().format(formatter),
                contato.getId(),
                contato.getNome(),
                contato.getIdade(),
                telefones
        );

        try (
                FileWriter fileWriter = new FileWriter(ARQUIVO_LOG, true);
                PrintWriter printWriter = new PrintWriter(fileWriter)
        ) {
            printWriter.println(linha);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao gravar log de exclusão", e);
        }
    }
}
