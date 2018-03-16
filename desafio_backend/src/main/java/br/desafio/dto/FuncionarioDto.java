package br.desafio.dto;

import java.util.Optional;
import br.desafio.enums.PerfilEnum;

public class FuncionarioDto {

	private Optional<Long> id = Optional.empty();
	private String nome;
	private String email;
	private PerfilEnum perfil;
	
	public FuncionarioDto (){
		
	}
	public Optional<Long> getId() {
		return id;
	}
	public void setId(Optional<Long> id) {
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
	public PerfilEnum getPerfil() {
		return perfil;
	}
	public void setPerfil(PerfilEnum perfil) {
		this.perfil = perfil;
	}
	@Override
	public String toString() {
		return "FuncionarioDto [id=" + id + ", nome=" + nome + ", email=" + email + ", perfil=" + perfil + "]";
	}
}
