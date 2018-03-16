package br.desafio.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.desafio.dto.CadastroPfDto;
import br.desafio.entities.Empresa;
import br.desafio.entities.Funcionario;
import br.desafio.enums.PerfilEnum;
import br.desafio.response.Response;
import br.desafio.service.EmpresaService;
import br.desafio.service.FuncionarioService;
import br.desafio.util.Password;

@RestController
@CrossOrigin(origins="*")
@RequestMapping("/admin")
public class CadastroPfController {

	private static final Logger log = LoggerFactory.getLogger(CadastroPfController.class);
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	public CadastroPfController(){
		
	}
	
	//Cadastra um funcionario na empresa, ja como USER. Para isso é preciso autenticar um 
	//funcionario cadastrado como ADMIN na classe CadastroPjController
    @RequestMapping(path="/api/cadastrarpf", method=RequestMethod.POST)
	public ResponseEntity<Response<CadastroPfDto>> cadastrar (@Valid @RequestBody CadastroPfDto cadastroPfDto,
			BindingResult result){
		
		log.info("Cadastrando PF:", cadastroPfDto);
		Response<CadastroPfDto> response = new Response<CadastroPfDto>();
		
		validarDados(cadastroPfDto, result);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPfDto, result);
		
		if(result.hasErrors()){
			log.error("Erro de validacao de dados:", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cadastroPfDto.getCnpj());
		empresa.ifPresent(emp -> funcionario.setEmpresa(emp));
		this.funcionarioService.persistir(funcionario);
		
		response.setData(this.converterCadastroPfDto(funcionario));
		return ResponseEntity.ok(response);
	}
	
	
    //Valida os dados que estão sendo buscados da empresa onde o funcionario está sendo cadastrado
	public void validarDados(CadastroPfDto cadastroPfDto, BindingResult result){
		Optional<Empresa> empresa = this.empresaService.buscarPorCnpj(cadastroPfDto.getCnpj());
		if(!empresa.isPresent()){
			result.addError(new ObjectError("empresa", "Empresa nao cadastrada"));
		}
	}
	
	private Funcionario converterDtoParaFuncionario(CadastroPfDto cadastroPfDto, BindingResult result){
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(cadastroPfDto.getNome());
		funcionario.setEmail(cadastroPfDto.getEmail());
		funcionario.setSenha(Password.geraBcrypt(cadastroPfDto.getSenha()));
		funcionario.setPerfil(PerfilEnum.ROLE_USER);
		cadastroPfDto.getQtdHorasAlmoco().ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
		cadastroPfDto.getQtdHorasDia().ifPresent(qtdHorasDia -> funcionario.setQtdHorasDia(Float.valueOf(qtdHorasDia)));
		return funcionario;
	}
	
	private CadastroPfDto converterCadastroPfDto(Funcionario funcionario){
		CadastroPfDto cadastroPfDto = new CadastroPfDto();
		cadastroPfDto.setId(funcionario.getId());
		cadastroPfDto.setNome(funcionario.getNome());
		cadastroPfDto.setEmail(funcionario.getEmail());
		cadastroPfDto.setCnpj(funcionario.getEmpresa().getCnpj());
		funcionario.getQtdHorasAlmocoOpt().ifPresent(qtdHorasAlmoco -> cadastroPfDto.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));
		funcionario.getQtdHorasDiaOpt().ifPresent(qtdHorasDia -> cadastroPfDto.setQtdHorasDia(Optional.of(Float.toString(qtdHorasDia))));
		
		return cadastroPfDto;
	}
}
