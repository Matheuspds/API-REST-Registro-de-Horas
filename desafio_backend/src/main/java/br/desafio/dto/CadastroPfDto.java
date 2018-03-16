package br.desafio.dto;

import java.util.Optional;

import org.hibernate.validator.constraints.NotEmpty;

public class CadastroPfDto {

	private Long id;
	
	@NotEmpty(message = "Nome nao pode ser vazio")
	private String nome;
	
	private String email;
	private String senha;
	private Optional<String> qtdHorasDia = Optional.empty();
	private Optional<String> qtdHorasAlmoco = Optional.empty();
	private String cnpj;
	
	public CadastroPfDto(){
		
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

	public Optional<String> getQtdHorasDia() {
		return qtdHorasDia;
	}

	public void setQtdHorasDia(Optional<String> qtdHorasDia) {
		this.qtdHorasDia = qtdHorasDia;
	}

	public Optional<String> getQtdHorasAlmoco() {
		return qtdHorasAlmoco;
	}

	public void setQtdHorasAlmoco(Optional<String> qtdHorasAlmoco) {
		this.qtdHorasAlmoco = qtdHorasAlmoco;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	@Override
	public String toString() {
		return "CadastroPfDto [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", qtdHorasDia="
				+ qtdHorasDia + ", qtdHorasAlmoco=" + qtdHorasAlmoco + ", cnpj=" + cnpj + "]";
	}
}
