package br.desafio.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class CadastroPjDto {

	private Long id;
	
	@NotEmpty(message = "Nome não pode ser vazio")
	private String nome;
	
	@NotEmpty(message = "Email não pode ser vazio")
	@Email(message="Email invalido")
	private String email;
	
	@NotEmpty(message = "Senha não pode ser vazia")
	private String senha;
	
	@NotEmpty(message = "Razao Social não pode ser vazia")
	private String razaoSocial;
	
	@NotEmpty(message = "CNPJ nao pode ser vazio")
	private String cnpj;
	
	public CadastroPjDto(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return "CadastroPjDto [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", razaoSocial="
				+ razaoSocial + ", cnpj=" + cnpj + "]";
	}
	
	
}
