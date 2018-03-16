package br.desafio.dto;

public class EmpresaDto {

	private Long id;
	private String cnpj;
	private String razaoSocial;
	
	public EmpresaDto(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@Override
	public String toString() {
		return "EmpresaDto [id=" + id + ", cnpj=" + cnpj + ", razaoSocial=" + razaoSocial + "]";
	}
	
	
}
