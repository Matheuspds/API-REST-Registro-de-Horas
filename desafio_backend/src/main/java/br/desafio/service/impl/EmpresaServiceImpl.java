package br.desafio.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.desafio.entities.Empresa;
import br.desafio.repository.EmpresaRepository;
import br.desafio.service.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService{

	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Cacheable("funcionarioCache")
	public Optional<Empresa> buscarPorCnpj(String cnpj){
		log.info("Buscando uma empresa pelo CNPJ:", cnpj);
		return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));
	}
	
	@CachePut("funcionarioCache")
	public Empresa persistir(Empresa empresa){
		log.info("Persistindo Empresa:", empresa);
		return this.empresaRepository.save(empresa);
	}
}
