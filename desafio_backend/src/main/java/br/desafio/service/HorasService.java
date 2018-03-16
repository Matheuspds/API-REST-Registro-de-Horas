package br.desafio.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.desafio.entities.Horas;

public interface HorasService {

	Page<Horas> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest);
	Optional<Horas> buscarPorId(Long id);
	Horas persistir(Horas horas);
	void remover(Long id);
}
