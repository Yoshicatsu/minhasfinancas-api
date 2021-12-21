package com.cursosudemy.minhasFinancas.api.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cursosudemy.minhasFinancas.api.dto.UsuarioDTO;
import com.cursosudemy.minhasFinancas.exception.ErroAutenticacao;
import com.cursosudemy.minhasFinancas.exception.RegraNegocioException;
import com.cursosudemy.minhasFinancas.model.entity.Usuario;
import com.cursosudemy.minhasFinancas.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@GetMapping("/helloworld")
	public String helloWorld() {
		return "hello world!";
	}

	private UsuarioService service;

	public UsuarioController(UsuarioService service) {
		this.service = service;
	}

	@PostMapping("/autenticar")
	public ResponseEntity autenticar(@RequestBody UsuarioDTO dto) {
		try{
			Usuario usuarioAutenticado = service.autenticar(dto.getEmail(), dto.getSenha());
			return ResponseEntity.ok(usuarioAutenticado);
		}catch (ErroAutenticacao e){
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	Usuario usuarioTeste = Usuario.builder().nome("teste1").email("email1@email.com").senha("12345").build();
	

	@PostMapping
	public ResponseEntity salvar(@RequestBody UsuarioDTO dto) {
		Usuario usuario = Usuario.builder().nome(dto.getNome()).email(dto.getEmail()).senha(dto.getSenha()).build();
		System.out.println(dto.getNome());
		System.out.println(dto.getEmail());
		System.out.println(dto.getSenha());
		System.out.println(usuarioTeste.getNome());
		System.out.println(usuarioTeste.getEmail());
		System.out.println(usuarioTeste.getSenha());
		Usuario usuarioTesteSalvo = service.salvarUsuario(usuario);
		System.out.println(usuarioTesteSalvo.getNome());
		System.out.println(usuarioTesteSalvo.getEmail());
		System.out.println(usuarioTesteSalvo.getSenha());
		try {
			Usuario usuarioSalvo = service.salvarUsuario(usuario);
			System.out.println(usuarioSalvo.getNome());
			System.out.println(usuarioSalvo.getEmail());
			System.out.println(usuarioSalvo.getSenha());

			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
		} catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
