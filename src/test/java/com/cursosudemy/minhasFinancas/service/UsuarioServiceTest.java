package com.cursosudemy.minhasFinancas.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cursosudemy.minhasFinancas.exception.RegraNegocioException;
import com.cursosudemy.minhasFinancas.model.entity.Usuario;
import com.cursosudemy.minhasFinancas.model.repository.UsuarioRepository;
import com.cursosudemy.minhasFinancas.service.impl.UsuarioServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

	@Autowired
	UsuarioService service;
	
	@Autowired
	UsuarioRepository repository;
	
	@Before
	public void setUp() {
		repository = Mockito.mock(UsuarioRepository.class);
		service = new UsuarioServiceImpl(repository);
	}
	
	@Test
	public void deveSalvarUmUsuario(String email) {
		//cenário
		
	}
	
	@Test(expected = Test.None.class)
	public void deveValidarEmail() {
		
		//cenário
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(false);
		
		//ação
		service.validarEmail("email@email.com");
		
	}
	
	@Test(expected = RegraNegocioException.class)
	public void deveLancarErroAoValidarEmailQuandoExistirEmailCadastrado() {
		//cenario
		Mockito.when(repository.existsByEmail(Mockito.anyString())).thenReturn(true);
		
		//Usuario usuario = Usuario.builder().nome("usuario").email("email@email.com").build(); //cria um usuário com email
		//repository.save(usuario); //salva o usuario na base de dados
		
		//ação
		service.validarEmail("email@email.com"); //checa se já existe usuário com o email criado
		
	}
}
