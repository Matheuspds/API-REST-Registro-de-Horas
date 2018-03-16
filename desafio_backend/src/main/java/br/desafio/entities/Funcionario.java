package br.desafio.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import br.desafio.enums.PerfilEnum;

@Entity(name="funcionario")
public class Funcionario implements Serializable{
	
	private static final long serialVersionUID = 3521616756309050292L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="nome", nullable=false)
	private String nome;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@Column(name="senha", nullable=false)
	private String senha;
	
	@Column(name="qtd_horas_dia", nullable=true)
	private Float qtdHorasDia;
	
	@Column(name="qtd_horas_almoco", nullable=true)
	private Float qtdHorasAlmoco;
	
	@Enumerated(EnumType.STRING)
	@Column(name="perfil", nullable=false)
	private PerfilEnum perfil;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Empresa empresa;
	
	@OneToMany(mappedBy="funcionario", fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	private List<Horas> horasLancadas;
	
	public Funcionario(){
		
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

	public Float getQtdHorasDia() {
		return qtdHorasDia;
	}

	@Transient
	public Optional<Float> getQtdHorasDiaOpt(){
		return Optional.ofNullable(qtdHorasDia);
	}
	
	public void setQtdHorasDia(Float qtdHorasDia) {
		this.qtdHorasDia = qtdHorasDia;
	}

	public Float getQtdHorasAlmoco() {
		return qtdHorasAlmoco;
	}

	@Transient
	public Optional<Float> getQtdHorasAlmocoOpt(){
		return Optional.ofNullable(qtdHorasAlmoco);
	}
	
	public void setQtdHorasAlmoco(Float qtdHorasAlmoco2) {
		this.qtdHorasAlmoco = qtdHorasAlmoco2;
	}

	public PerfilEnum getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilEnum perfil) {
		this.perfil = perfil;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public List<Horas> getHorasLancadas() {
		return horasLancadas;
	}

	public void setHorasLancadas(List<Horas> horasLancadas) {
		this.horasLancadas = horasLancadas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Funcionario [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", qtdHorasDia="
				+ qtdHorasDia + ", qtdHorasAlmoco=" + qtdHorasAlmoco + ", perfil=" + perfil + ", empresa=" + empresa
				+ ", horasLancadas=" + horasLancadas + "]";
	}
}
