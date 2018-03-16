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

import br.desafio.entities.Funcionario;
import br.desafio.repository.FuncionarioRepository;
import br.desafio.service.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService{

	private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@CachePut("funcionarioCache")
	public Funcionario persistir(Funcionario funcionario){
		log.info("Persistindo Funcionario:", funcionario);
		return this.funcionarioRepository.save(funcionario);
	}
	
	@Cacheable("funcionarioCache")
	public Optional<Funcionario> buscarPorId (Long id){
		log.info("Buscando Funcionario por Id:", id);
		return Optional.ofNullable(this.funcionarioRepository.findOne(id));
	}
	
	@Cacheable("funcionarioCache")
	public Optional<Funcionario> buscarPorEmail(String email){
		log.info("Buscando Funcionario por Email:", email);
		return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
	}
	
	@Cacheable("funcionarioCache")
	public Page<Funcionario> buscarPorFuncionarios(Long empresaId, PageRequest pageRequest){
		log.info("Buscando por Funcionarios:", empresaId, pageRequest);
		return this.funcionarioRepository.findByEmpresaId(empresaId, pageRequest);
	}
	
	@Cacheable("funcionarioCache")
	public Funcionario buscarPorEmailAndSenha(String email, String senha){
		return funcionarioRepository.findByEmailAndSenha(email, senha);
	}
	
	@Cacheable("funcionarioCache")
	public Funcionario buscarPorNome(String nome){
		log.info("Buscando Funcionario por Email e Senha:", nome);
		return funcionarioRepository.findByNome(nome);

	}
}
