package br.desafio.repository;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.desafio.entities.Empresa;
import br.desafio.entities.Funcionario;
import br.desafio.entities.Horas;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	Empresa findByCnpj(String cnpj);

}
