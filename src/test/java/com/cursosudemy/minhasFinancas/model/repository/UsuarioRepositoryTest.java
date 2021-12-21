package com.cursosudemy.minhasFinancas.model.repository;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cursosudemy.minhasFinancas.model.entity.Usuario;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioRepositoryTest { //testa inicializando toda a SpringBoot
	
	@Autowired
	UsuarioRepository repository;
	
	//Para realizar um teste precisa-se de 3 elementos: cenario, ação/ , verificação
	@Test
	public void deveVerificarAExistenciaDeUmEmail() {
		
		//cenario
		Usuario usuario = Usuario.builder().nome("usuario").email("usuario@email.com").build(); //cria um usuário
		repository.save(usuario); //salva o usuário gerado na base do banco de dados
		
		
		//ação / execução
		boolean result = repository.existsByEmail("usuario@email.com"); //resultado da verificação do método existsByEmail()
		
		//verificação
		Assertions.assertThat(result).isTrue(); //verifica se o resultado de result retorna true
		
	}
	
	@Test
	public void deveRetornarFalsoQuandoNaoHouverUsuarioCadastradoComOEmail() {
		
		//cenário
		repository.deleteAll(); //deverá deletar todos os usuários da database, para garantir que não haja com aquele e-mail
		
		//ação
		boolean result = repository.existsByEmail("usuario@email.com");
		
		//verificação
		Assertions.assertThat(result).isFalse();
	}
	
	

}
