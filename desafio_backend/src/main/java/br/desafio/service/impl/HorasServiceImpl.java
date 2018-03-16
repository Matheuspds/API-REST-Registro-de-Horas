package br.desafio.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.desafio.entities.Horas;
import br.desafio.repository.HorasRepository;
import br.desafio.service.HorasService;

@Service
public class HorasServiceImpl implements HorasService{

	private static final Logger log = LoggerFactory.getLogger(HorasServiceImpl.class);

	@Autowired
	private HorasRepository horasRepository;
	
	@Cacheable("funcionarioCache")
	public Page<Horas> buscarPorFuncionarioId (Long funcionarioId, PageRequest pageRequest){
		log.info("Buscando Horas de um Funcionario pelo ID:", funcionarioId);
		return this.horasRepository.findByFuncionarioId(funcionarioId, pageRequest);
	}
	
	@Cacheable("funcionarioCache")
	public Optional<Horas> buscarPorId(Long id){
		log.info("Buscando determinadas horas registradas:", id);
		return Optional.ofNullable(horasRepository.findOne(id));
	}
	
	@CachePut("funcionarioCache")
	public Horas persistir (Horas horas){
		log.info("Persistindo Horas:", horas);
		return this.horasRepository.save(horas);
	}
	
	public void remover(Long id){
		log.info("Remover Horas registradas:", id);
		this.horasRepository.delete(id);
	}
	
	
	
}
