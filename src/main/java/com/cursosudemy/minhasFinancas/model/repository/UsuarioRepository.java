package com.cursosudemy.minhasFinancas.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cursosudemy.minhasFinancas.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByEmail(String email);
	
	Optional<Usuario> findByNome(String nome);
	
	Optional<Usuario> findByEmailAndNome(String email, String nome);
	
	boolean existsByEmail(String email);
}
