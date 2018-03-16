package br.desafio.controller;


import java.text.SimpleDateFormat;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.desafio.dto.EmpresaDto;
import br.desafio.dto.FuncionarioDto;
import br.desafio.dto.HorasDto;
import br.desafio.entities.Empresa;
import br.desafio.entities.Funcionario;
import br.desafio.entities.Horas;
import br.desafio.response.Response;
import br.desafio.service.EmpresaService;
import br.desafio.service.FuncionarioService;
import br.desafio.service.HorasService;

@RestController
@RequestMapping("api/empresas")
@CrossOrigin(origins = "*")
public class EmpresaController {

	private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
    private HorasService horasService;
	
	@Value("${paginacao.qtd_por_pagina}")
    private int qtdPagina;
	
	public EmpresaController(){
		
	}
    
	//Retorna a relação de horas de um determinado funcionario, que trabalha em uma empresa
    @RequestMapping(path="/horas/funcionario/{funcionarioId}", method=RequestMethod.GET)
    public ResponseEntity<Response<org.springframework.data.domain.Page<HorasDto>>> listarPorFuncionarioId(@PathVariable("funcionarioId") Long funcionarioId,
    		@RequestParam(value = "pag", defaultValue = "0") int pag,
    		@RequestParam(value = "ord", defaultValue = "id") String ord,
    		@RequestParam(value = "dir", defaultValue = "DESC") String dir){
        
    	log.info("Buscando Horas por Funcionario", funcionarioId, pag);
        Response<org.springframework.data.domain.Page<HorasDto>> response = new Response<org.springframework.data.domain.Page<HorasDto>>();
        PageRequest pageRequest = new PageRequest(pag, qtdPagina, Direction.valueOf(dir), ord);
        org.springframework.data.domain.Page<Horas> horas = this.horasService.buscarPorFuncionarioId(funcionarioId, pageRequest);
        org.springframework.data.domain.Page<HorasDto> horasDto = horas.map(hora -> this.converterHorasDto(hora));
        
        response.setData(horasDto);
        return ResponseEntity.ok(response);
    }

    private HorasDto converterHorasDto(Horas horas){
        HorasDto horasDto = new HorasDto();
        horasDto.setId(Optional.of(horas.getId()));
        horasDto.setDescricao(horas.getDescricao());
        horasDto.setData(this.dateFormat.format(horas.getData()));
        horasDto.setTipo(horas.getTipoHora().toString());
        horasDto.setFuncionarioId(horas.getFuncionario().getId());
        
        return horasDto;
    }
	
    //Retorna uma empresa realizando sua busca pelo cnpj
	@GetMapping(value = "/cnpj/{cnpj}")
	public ResponseEntity<Response<EmpresaDto>> buscarPorCnpj(@PathVariable("cnpj") String cnpj){
		log.info("Buscando Empresa", cnpj);
		Response<EmpresaDto> response = new Response<EmpresaDto>();
		Optional<Empresa> empresa = empresaService.buscarPorCnpj(cnpj);
		
		if(!empresa.isPresent()){
			log.info("Empresa nao encontrada" + cnpj);
			response.getErros().add("Empresa nao encontrada" + cnpj);
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(this.converterEmpresaDto(empresa.get()));
		return ResponseEntity.ok(response);
	}
	
	//Retorna a lista dos funcionarios cadastrados em uma determinada empresa 
	@GetMapping(value = "/funcionarios/{empresaId}")
    public ResponseEntity<Response<Page<FuncionarioDto>>> buscarPorFuncionarios(@PathVariable("empresaId") Long empresaId,
    		@RequestParam(value = "pag", defaultValue = "0") int pag,
    		@RequestParam(value = "ord", defaultValue = "id") String ord,
    		@RequestParam(value = "dir", defaultValue = "DESC") String dir){
        
    	log.info("Buscando por Funcionarios", empresaId, pag);
        Response<Page<FuncionarioDto>> response = new Response<Page<FuncionarioDto>>();
        PageRequest pageRequest = new PageRequest(pag, qtdPagina, Direction.valueOf(dir), ord);
        Page<Funcionario> funcionarios = this.funcionarioService.buscarPorFuncionarios(empresaId, pageRequest);
        Page<FuncionarioDto> funcionarioDto = funcionarios.map(funcionario -> this.converterFuncionarioDto(funcionario));
        
        response.setData(funcionarioDto);
        return ResponseEntity.ok(response);
    }
	
	private FuncionarioDto converterFuncionarioDto(Funcionario funcionario){
		FuncionarioDto funcionarioDto = new FuncionarioDto();
		funcionarioDto.setId(Optional.of(funcionario.getId()));
		funcionarioDto.setNome(funcionario.getNome());
		funcionarioDto.setEmail(funcionario.getEmail());
		funcionarioDto.setPerfil(funcionario.getPerfil());
		
		return funcionarioDto;
	}
	
	private EmpresaDto converterEmpresaDto(Empresa empresa){
		EmpresaDto empresaDto = new EmpresaDto();
		empresaDto.setId(empresa.getId());
		empresaDto.setRazaoSocial(empresa.getRazaoSocial());
		empresaDto.setCnpj(empresa.getCnpj());
		
		return empresaDto;
	}
	
}
