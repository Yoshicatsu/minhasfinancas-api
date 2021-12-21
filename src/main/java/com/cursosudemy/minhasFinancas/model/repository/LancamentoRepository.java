package com.cursosudemy.minhasFinancas.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursosudemy.minhasFinancas.model.entity.Lancamento;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{

}
