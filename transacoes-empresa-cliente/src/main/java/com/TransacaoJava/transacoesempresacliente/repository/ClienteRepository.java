package com.TransacaoJava.transacoesempresacliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TransacaoJava.transacoesempresacliente.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
