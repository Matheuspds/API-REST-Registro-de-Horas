package br.desafio.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.desafio.enums.HoraEnum;

@Entity(name="horas")
public class Horas implements Serializable{

	private final static long serialVersionUID = 33995858L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data", nullable=false)
	private Date data;
	
	@Column(name="descricao", nullable=true)
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_hora", nullable=false)
	private HoraEnum tipoHora;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private Funcionario funcionario;
	
	public Horas (){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public HoraEnum getTipoHora() {
		return tipoHora;
	}

	public void setTipoHora(HoraEnum tipoHora) {
		this.tipoHora = tipoHora;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Horas [id=" + id + ", data=" + data + ", descricao=" + descricao + ", tipoHora=" + tipoHora
				+ ", funcionario=" + funcionario + "]";
	}
}
