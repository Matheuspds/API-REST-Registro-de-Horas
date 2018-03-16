package br.desafio.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.desafio.entities.Funcionario;

public interface FuncionarioService {

	Funcionario persistir(Funcionario funcionario);
	Optional<Funcionario> buscarPorEmail (String email);
	Optional<Funcionario> buscarPorId (Long id);
	Page<Funcionario> buscarPorFuncionarios(Long empresaId, PageRequest pageRequest);
	Funcionario buscarPorEmailAndSenha(String email, String senha);
	Funcionario buscarPorNome(String nome);

}
