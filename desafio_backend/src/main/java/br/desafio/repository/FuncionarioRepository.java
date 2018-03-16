package br.desafio.repository;


import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.desafio.entities.Funcionario;

@Transactional
@NamedQueries({
	@NamedQuery(name = "FuncionarioRepository.findByEmpresaId",
				query = "SELECT f from funcionario f where f.empresa.id = :empresaId")
})
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	@Query(value =" SELECT f FROM funcionario f where f.nome = :pnome")
	Funcionario findByNome(@Param("pnome") String nome);
	
	@Query(value = "SELECT f FROM funcionario f where f.email = :pemail")
	Funcionario findByEmail(@Param("pemail") String email);
	Funcionario findByNomeOrEmail(String nome, String email);
	Page<Funcionario> findByEmpresaId(@Param("empresaId") Long empresaId, Pageable pageable);
	
	@Query(value = "select f from funcionario f where f.email = :pemail and f.senha = :psenha")
	Funcionario findByEmailAndSenha(@Param("pemail") String email, @Param("psenha") String senha);
}
