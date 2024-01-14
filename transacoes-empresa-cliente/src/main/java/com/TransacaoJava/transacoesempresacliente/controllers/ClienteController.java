package com.TransacaoJava.transacoesempresacliente.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.TransacaoJava.transacoesempresacliente.models.Cliente;
import com.TransacaoJava.transacoesempresacliente.repository.ClienteRepository;
import com.TransacaoJava.transacoesempresacliente.service.ClienteService;
import com.TransacaoJava.transacoesempresacliente.service.EmpresaService;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class ClienteController {

    private final ClienteService clienteService;
    
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EmpresaService empresaService;
    
    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/processar-cadastro-cliente")
    public String processarCadastroCliente(@RequestParam String nome, @RequestParam String cpf, @RequestParam String senha) {
        clienteService.cadastrarCliente(nome, cpf, senha);
        return "redirect:/login-cliente";
    }
    
    @PostMapping("/processar-login-cliente")
    public String processarLoginCliente(@RequestParam String cpf, @RequestParam String senha) {
    	if(clienteService.validarCredenciais(cpf, senha)) {
    		return "redirect:/empresas-cadastradas";
    	} else {
    		return "redirect:/login-cliente?erro=credenciais-invalidas";
    	}
    }
    
    //Não deu certo, possível refatoração futura
	/*
	 * @PostMapping("/associar-cliente-empresa") public String
	 * associarClienteAEmpresa(@RequestParam Long clienteId, @RequestParam Long
	 * empresaId, Model model) { try {
	 * clienteService.associarClienteAEmpresa(clienteId, empresaId, new
	 * BigDecimal("400.00")); return "redirect:/pagina-de-sucesso"; // Redirecione
	 * para uma página de sucesso } catch (SaldoInsuficienteException e) {
	 * model.addAttribute("erro", e.getMessage()); return
	 * "redirect:/empresas-cadastradas"; // Redirecione de volta para a lista de
	 * empresas com mensagem de erro } }
	 */
}
