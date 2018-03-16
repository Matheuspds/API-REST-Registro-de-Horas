package br.desafio.controller;

import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.desafio.dto.CadastroPjDto;
import br.desafio.entities.Empresa;
import br.desafio.entities.Funcionario;
import br.desafio.enums.PerfilEnum;
import br.desafio.response.Response;
import br.desafio.service.EmpresaService;
import br.desafio.service.FuncionarioService;
import br.desafio.util.Password;

@RestController
@RequestMapping("api/cadastrarpj")
@CrossOrigin(origins = "*")
public class CadastroPjController {

	private static final Logger log = LoggerFactory.getLogger(CadastroPjController.class);
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private EmpresaService empresaService;
	
	public CadastroPjController(){
		
	}
	
	//Metodo que cadastra uma empresa e junto a ela um funcionario com o papel de ADMIN
	//esse mesmo funcionario poderá ser autenticado para que possa cadastrar demais funcionarios
	//e também realizar o cadastro de horas
	@PostMapping
	public ResponseEntity<Response<CadastroPjDto>> cadastrar (@Valid @RequestBody CadastroPjDto cadastroPjDto,
			BindingResult result) throws NoSuchAlgorithmException {
		
		log.info("Cadastrando PJ:", cadastroPjDto);
		Response<CadastroPjDto> response = new Response<CadastroPjDto>();
		
		validarDados(cadastroPjDto, result);
		Empresa empresa = this.converterDtoParaEmpresa(cadastroPjDto);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPjDto, result);
		
		if(result.hasErrors()){
			log.error("Erro de validacao de dados:", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.empresaService.persistir(empresa);
		funcionario.setEmpresa(empresa);
		this.funcionarioService.persistir(funcionario);
		
		response.setData(this.converterCadastroPjDto(funcionario));
		return ResponseEntity.ok(response);
	}
	
	
	public void validarDados(CadastroPjDto cadastroPjDto, BindingResult result){
		this.empresaService.buscarPorCnpj(cadastroPjDto.getCnpj()).ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa ja existente")));
	}
	
	private Empresa converterDtoParaEmpresa(CadastroPjDto cadastroPjDto){
		Empresa empresa = new Empresa();
		empresa.setCnpj(cadastroPjDto.getCnpj());
		empresa.setRazaoSocial(cadastroPjDto.getRazaoSocial());
		return empresa;
	}
	
	private Funcionario converterDtoParaFuncionario(CadastroPjDto cadastroPjDto, BindingResult result){
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(cadastroPjDto.getNome());
		funcionario.setEmail(cadastroPjDto.getEmail());
		funcionario.setSenha(Password.geraBcrypt(cadastroPjDto.getSenha()));
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
		
		return funcionario;
	}
	
	private CadastroPjDto converterCadastroPjDto(Funcionario funcionario){
		CadastroPjDto cadastroPjDto = new CadastroPjDto();
		cadastroPjDto.setId(funcionario.getId());
		cadastroPjDto.setNome(funcionario.getNome());
		cadastroPjDto.setEmail(funcionario.getEmail());
		cadastroPjDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		cadastroPjDto.setCnpj(funcionario.getEmpresa().getCnpj());
		
		return cadastroPjDto;
	}
}
