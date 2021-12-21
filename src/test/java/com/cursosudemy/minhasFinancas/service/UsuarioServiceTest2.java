package com.cursosudemy.minhasFinancas.service;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.cursosudemy.minhasFinancas.exception.ErroAutenticacao;
import com.cursosudemy.minhasFinancas.exception.RegraNegocioException;
import com.cursosudemy.minhasFinancas.model.entity.Usuario;
import com.cursosudemy.minhasFinancas.model.repository.UsuarioRepository;
import com.cursosudemy.minhasFinancas.service.impl.UsuarioServiceImpl;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest2 {

	@Autowired
	UsuarioService service;
	
	@MockBean
	UsuarioRepository repository;
	
	@Before
	public void setUp() {
		repository = Mockito.mock(UsuarioRepository.class);
		service = new UsuarioServiceImpl(repository);
	}
	
	@Test
	public void deveAutenticarUmUsuarioComSucesso() {
		//cenário
		String email = "email@email.com";
		String senha = "senha";
		
		Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
		Mockito.when(repository.findByEmail(email)).thenReturn(Optional.of(usuario));
		
		//ação
		Usuario result = service.autenticar(email, senha);
		
		//verificação
		Assertions.assertThat(result).isNotNull(); 
	}
	
	@Test
	public void deveLancarerroQuandoNaoEncontrarUsuarioCadastradoComOEmailInformado() {
		//cenário
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		//ação
		Throwable exception = Assertions.catchThrowable ( () -> service.autenticar("email@email.com", "senha"));
		//service.autenticar está verificando um optional (existe um usuário com e-mail "email.@email.com e cuja senha é "senha"?)
		//assertions vai capturar um throwable. Throwable "exception" recebe esse throwable do e-mail.
		
		//verificação
		Assertions.assertThat(exception)
		.isInstanceOf(ErroAutenticacao.class)
		.hasMessage("Usuario não encontrado para o e-mail informado.");
	}
	
	@Test
	public void deveLancarErroQuandoSenhaNaoBater() {
		//cenário
		String senha = "senha";
		Usuario usuario = Usuario.builder().email("email@email.com").senha(senha).build();
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));
		
		//ação
		Throwable exception = Assertions.catchThrowable( () -> service.autenticar("email@email.com", "123 "));
		
		//verificação
		Assertions.assertThat(exception)
		.isInstanceOf(ErroAutenticacao.class)
		.hasMessage("Senha inválida.");
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
