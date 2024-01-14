package com.TransacaoJava.transacoesempresacliente.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TransacaoJava.transacoesempresacliente.service.TransacaoService;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {
    @Autowired
    private TransacaoService transacaoService;

    @PostMapping("/realizar-deposito")
    public ResponseEntity<String> realizarDeposito(@RequestParam Long clienteId, @RequestParam BigDecimal valor) {
        transacaoService.realizarDeposito(clienteId, valor);
        return ResponseEntity.ok("Depósito realizado com sucesso");
    }
}