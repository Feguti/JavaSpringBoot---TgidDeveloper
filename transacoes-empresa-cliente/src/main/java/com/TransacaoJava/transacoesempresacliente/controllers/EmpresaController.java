package com.TransacaoJava.transacoesempresacliente.controllers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TransacaoJava.transacoesempresacliente.models.Empresa;
import com.TransacaoJava.transacoesempresacliente.service.EmpresaService;

@Controller
public class EmpresaController {

    private final EmpresaService empresaService;

    @Autowired
    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @PostMapping("/processar-cadastro-empresa")
    public String processarCadastroEmpresa(@RequestParam String nome, @RequestParam String cnpj, @RequestParam String senha) {
        empresaService.cadastrarEmpresa(nome, cnpj, senha);
        return "redirect:/login-empresa";
    }
    
    @PostMapping("/processar-login-empresa")
    public String processarLoginEmpresa(@RequestParam String cnpj, @RequestParam String senha) {
        if(empresaService.validarCredenciais(cnpj, senha)) {
            return "redirect:/perfil-empresa";
        } else {
            return "redirect:/login-empresa?erro=credenciais-invalidas";
        }
    }
    
    @GetMapping("/empresas-cadastradas")
    public String exibirEmpresas(Model model) {
        // Obtém a lista de empresas do serviço
        List<Empresa> empresas = empresaService.obterTodasEmpresas();

        // Adiciona a lista de empresas ao modelo
        model.addAttribute("empresas", empresas);

        return "empresas-cadastradas";
    }
}