package com.TransacaoJava.transacoesempresacliente.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TransacaoJava.transacoesempresacliente.models.Cliente;
import com.TransacaoJava.transacoesempresacliente.models.Empresa;
import com.TransacaoJava.transacoesempresacliente.repository.ClienteRepository;
import com.TransacaoJava.transacoesempresacliente.repository.EmpresaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;
    
    @Autowired
    public ClienteService(ClienteRepository clienteRepository, EmpresaRepository empresaRepository) {
        this.clienteRepository = clienteRepository;
        this.empresaRepository = empresaRepository;
    }
    public Cliente cadastrarCliente(String nome, String cpf, String senha) {
        Cliente novoCliente = new Cliente();
        novoCliente.setNome(nome);
        novoCliente.setCpf(cpf);
        novoCliente.setSenha(senha);
        
        //Para fins de avaliação e funcionalidade, o saldo inicial de todo cliente recém cadastrado será de 500.00
        novoCliente.setSaldo(new BigDecimal("500.00"));;

        return clienteRepository.save(novoCliente);
    }
    
    public boolean validarCredenciais(String cpf, String senha) {
    	Optional<Cliente> clienteOptional = clienteRepository.findByCpf(cpf);
    	
    	return clienteOptional.isPresent() && clienteOptional.get().getSenha().equals(senha);
    }
    
    public void associarClienteAEmpresa(Long clienteId, Long empresaId, BigDecimal taxaAssociacao) throws EntityNotFoundException {
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        // Verifique se o cliente tem saldo suficiente
        if (cliente.getSaldo().compareTo(taxaAssociacao) >= 0) {
            // Desconte a taxa do saldo do cliente
            cliente.setSaldo(cliente.getSaldo().subtract(taxaAssociacao));

            // Associe o cliente à empresa
            Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada"));
            cliente.setEmpresaAssociada(empresa);

            // Atualize o cliente no banco de dados
            clienteRepository.save(cliente);
        } else {
            throw new SaldoInsuficienteException("Saldo insuficiente para associar-se à empresa");
        }
    }
}



