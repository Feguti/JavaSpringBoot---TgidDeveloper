package com.TransacaoJava.transacoesempresacliente.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.TransacaoJava.transacoesempresacliente.controllers.SaldoInsuficienteException;
import com.TransacaoJava.transacoesempresacliente.models.Cliente;
import com.TransacaoJava.transacoesempresacliente.models.Empresa;
import com.TransacaoJava.transacoesempresacliente.repository.ClienteRepository;
import com.TransacaoJava.transacoesempresacliente.repository.EmpresaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class TransacaoService {
    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public void realizarDeposito(Long clienteId, BigDecimal valor) {
        // 1. Encontrar o cliente logado
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new EntityNotFoundException("Cliente não encontrado"));

        // 2. Verificar se o saldo do cliente é suficiente para o depósito
        if (cliente.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo do cliente é insuficiente para realizar o depósito");
        }

        // 3. Reduzir o saldo do cliente
        cliente.setSaldo(cliente.getSaldo().subtract(valor));
        clienteRepository.save(cliente);

        // 4. Encontrar a empresa para depositar
        Empresa empresa = empresaRepository.findById(1L) 
                .orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada"));

        // 5. Realizar o depósito na empresa
        empresa.setSaldo(empresa.getSaldo().add(valor));
        empresaRepository.save(empresa);
    }
}