package br.desafio.controller;

import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import br.desafio.entities.Funcionario;
import br.desafio.service.FuncionarioService;
import br.desafio.util.Password;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
public class LoginController {

	@Autowired
	private FuncionarioService funcionarioService;

	//Autentica um funcionario com email e senha para ter acesso a partir de /admin
	@RequestMapping(value="/autenticar", consumes= MediaType.APPLICATION_JSON_VALUE, method=RequestMethod.POST)
	public LoginResponse autenticar(@RequestBody Funcionario funcionario) throws ServletException{
		
		if(funcionario.getEmail() == null || funcionario.getSenha() == null){
			throw new ServletException("Nome e senha sao obrigatorios");
		}
		Funcionario funcAutenticado = funcionarioService.buscarPorEmailAndSenha(funcionario.getEmail(), Password.geraBcrypt(funcionario.getSenha()));

		if(funcAutenticado == null){
			throw new ServletException("Funcionario ADMIN nao encontrado");
		}
		
		String token = Jwts.builder().setSubject(funcAutenticado.getNome())
						.signWith(SignatureAlgorithm.HS512, "secret")
						.setExpiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
						.compact();
		
		return new LoginResponse(token);
	}
	
	private class LoginResponse {
		public String token;
		
		public String getToken() {
			return token;
		}
		public LoginResponse(String token) {
			// TODO Auto-generated constructor stub
			this.token = token;
		}
		
	}
}
