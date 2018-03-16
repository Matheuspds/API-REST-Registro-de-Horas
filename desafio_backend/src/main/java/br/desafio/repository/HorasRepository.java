package br.desafio.repository;

import java.util.Optional;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.desafio.entities.Horas;

@Transactional(readOnly = true)
@NamedQueries({
	@NamedQuery(name = "HorasRepository.findByFuncionarioId",
				query = "SELECT hr from horas hr where hr.funcionario.id = :funcionarioId")
})
@Repository
public interface HorasRepository extends JpaRepository<Horas, Long>{

	Optional<Horas> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);
	org.springframework.data.domain.Page<Horas> findByFuncionarioId(@Param("funcionarioId") Long funcionarioId, Pageable pageable);
}
