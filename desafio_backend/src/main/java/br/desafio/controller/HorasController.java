package br.desafio.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import br.desafio.dto.HorasDto;
import br.desafio.entities.Funcionario;
import br.desafio.entities.Horas;
import br.desafio.enums.HoraEnum;
import br.desafio.response.Response;
import br.desafio.service.FuncionarioService;
import br.desafio.service.HorasService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class HorasController {

    private static final Logger log = LoggerFactory.getLogger(HorasController.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    @Autowired
    private HorasService horasService;
    
    @Autowired
    private FuncionarioService funcionarioService;
    
    @Value("${paginacao.qtd_por_pagina}")
    private int qtdPagina;
    
    public HorasController (){
        
    }

    //Realiza o cadastro de horas de um determinado funcionario de uma empresa
    @RequestMapping(path="/api/horas", method=RequestMethod.POST)
    public ResponseEntity<Response<HorasDto>> adicionar(@Valid @RequestBody HorasDto horasDto, BindingResult result) throws ParseException{
        log.info("Adicionando Horas", horasDto.toString());
        Response<HorasDto> response = new Response<HorasDto>();
        validarFuncionario(horasDto, result);
        Horas horas = this.converterDtoParaHoras(horasDto, result);
        
        if(result.hasErrors()){
            log.error("Erro ao lançar as horas", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErros().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        horas = this.horasService.persistir(horas);
        response.setData(this.converterHorasDto(horas));
        return ResponseEntity.ok(response);
    }
    
    //Realiza a validação dos dados do funcionario do qual as horas serão registradas
    public void validarFuncionario(HorasDto horasDto, BindingResult result){
        if(horasDto.getFuncionarioId() == null){
            result.addError(new ObjectError("funcionario", "Funcionario nao informado"));
            return;
        }
        
        log.info("Validando funcionario", horasDto.getFuncionarioId());
        Optional<Funcionario> funcionario = this.funcionarioService.buscarPorId(horasDto.getFuncionarioId());
        if(!funcionario.isPresent()){
            result.addError(new ObjectError("funcionario", "Funcionario nao encontrado"));
        }
    }
    
    private Horas converterDtoParaHoras(HorasDto horasDto, BindingResult result) throws ParseException{
        Horas horas = new Horas();
        if(horasDto.getId().isPresent()){
            Optional<Horas> hr = this.horasService.buscarPorId(horasDto.getId().get());
            if(hr.isPresent()){
                horas = hr.get();
            } else{
                result.addError(new ObjectError("horas", "horas nao encontradas"));
            }
        }else{
            horas.setFuncionario(new Funcionario());
            horas.getFuncionario().setId(horasDto.getFuncionarioId());
        }
        horas.setData(this.dateFormat.parse(horasDto.getData()));
        horas.setDescricao(horasDto.getDescricao());
        
        if(horasDto.getTipo() != null){
            horas.setTipoHora(HoraEnum.valueOf(horasDto.getTipo()));
        }else{
            result.addError(new ObjectError("tipo", "tipo nao é valido"));
        }
    
        return horas;
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
    


}
