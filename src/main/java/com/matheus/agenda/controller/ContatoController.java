package com.matheus.agenda.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.matheus.agenda.model.Contato;
import com.matheus.agenda.model.Telefone;
import com.matheus.agenda.service.ContatoService;

@Controller
public class ContatoController {

    private final ContatoService contatoService;

    public ContatoController(ContatoService contatoService) {
        this.contatoService = contatoService;
    }

    @GetMapping("/")
    public String inicio() {
        return "redirect:/contatos";
    }

    @GetMapping("/contatos")
    public String pesquisar(
            @RequestParam(required = false, defaultValue = "todos") String tipo,
            @RequestParam(required = false) String termo,
            Model model
    ) {
        model.addAttribute("contatos", contatoService.pesquisar(tipo, termo));
        model.addAttribute("tipo", tipo);
        model.addAttribute("termo", termo);
        return "pesquisa";
    }

    @GetMapping("/contatos/novo")
    public String novo(Model model) {
        Contato contato = new Contato();
        contato.adicionarTelefone(new Telefone());

        model.addAttribute("contato", contato);
        model.addAttribute("titulo", "Cadastrar Contato");

        return "formulario";
    }

    @PostMapping("/contatos/salvar")
    public String salvar(
            @Valid @ModelAttribute Contato contato,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", contato.getId() == null ? "Cadastrar Contato" : "Alterar Contato");
            return "formulario";
        }

        contatoService.salvar(contato);

        return "redirect:/contatos";
    }

    @GetMapping("/contatos/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model
    ) {
        Contato contato = contatoService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado"));

        if (contato.getTelefones().isEmpty()) {
            contato.adicionarTelefone(new Telefone());
        }

        model.addAttribute("contato", contato);
        model.addAttribute("titulo", "Alterar Contato");

        return "formulario";
    }

    @GetMapping("/contatos/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        contatoService.excluir(id);
        return "redirect:/contatos";
    }

    @GetMapping("/contatos/cadastro")
    public String cadastro() {
        return "redirect:/contatos/novo";
    }
}
