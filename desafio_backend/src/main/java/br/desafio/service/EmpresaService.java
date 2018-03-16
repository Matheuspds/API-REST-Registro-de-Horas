package br.desafio.service;

import java.util.Optional;
import br.desafio.entities.Empresa;

public interface EmpresaService {

	Optional<Empresa> buscarPorCnpj(String cnpj);
	Empresa persistir(Empresa empresa);

}
