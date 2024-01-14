package com.TransacaoJava.transacoesempresacliente.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TransacaoJava.transacoesempresacliente.models.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long>{

}
