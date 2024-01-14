package com.TransacaoJava.transacoesempresacliente.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TransacaoJava.transacoesempresacliente.models.Cliente;
import com.TransacaoJava.transacoesempresacliente.models.Empresa;
import com.TransacaoJava.transacoesempresacliente.repository.EmpresaRepository;

@Service
public class EmpresaService {
	
	 private final EmpresaRepository empresaRepository;

	 @Autowired
	 public EmpresaService(EmpresaRepository empresaRepository) {
		 this.empresaRepository = empresaRepository;
	 }

	 public Empresa cadastrarEmpresa(String nome, String cnpj, String senha) {
		 Empresa novoEmpresa = new Empresa();
		 novoEmpresa.setNome(nome);
		 novoEmpresa.setCnpj(cnpj);
		 novoEmpresa.setSenha(senha);
		 
	     //Para fins de avaliação e funcionalidade, o saldo inicial de toda empresa recém cadastrado será de 1500.00
	     novoEmpresa.setSaldo(new BigDecimal("1500.00"));

		 return empresaRepository.save(novoEmpresa);
	 }
	 
	    public boolean validarCredenciais(String cnpj, String senha) {
	    	Optional<Empresa> empresaOptional = empresaRepository.findByCnpj(cnpj);
	    	
	    	return empresaOptional.isPresent() && empresaOptional.get().getSenha().equals(senha);
	    }
	 
	    public List<Empresa> obterTodasEmpresas() {
	        // Retorna a lista de todas as empresas do banco de dados
	        return empresaRepository.findAll();
	    }
	    
	    
}
