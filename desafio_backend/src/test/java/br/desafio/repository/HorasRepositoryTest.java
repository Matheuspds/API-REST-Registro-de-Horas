package br.desafio.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.desafio.entities.Empresa;
import br.desafio.entities.Funcionario;
import br.desafio.entities.Horas;
import br.desafio.enums.HoraEnum;
import br.desafio.enums.PerfilEnum;
import br.desafio.util.Password;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class HorasRepositoryTest {

	@Autowired
	private HorasRepository horasRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	private Long funcionarioId;
	
	private Empresa obterEmpresa(){
		Empresa empresa = new Empresa();
		empresa.setCnpj("123456789");
		empresa.setRazaoSocial("Empresa Exemplo");
		return empresa;
	}
	
	private Funcionario obterFuncionario(Empresa empresa){
		Funcionario funcionario = new Funcionario();
		funcionario.setEmail("exemplo@gmail.com");
		funcionario.setNome("Matheus Pereira Exemplo");
		funcionario.setSenha(Password.geraBcrypt("123"));
		funcionario.setEmpresa(empresa);
		funcionario.setPerfil(PerfilEnum.ROLE_USER);
		return funcionario;
	}
	
	private Horas obterHoras(Funcionario funcionario){
		Horas horas = new Horas();
		horas.setData(new Date());
		horas.setTipoHora(HoraEnum.INI_ALMOCO);
		horas.setFuncionario(funcionario);
		return horas;
	}
	
	@Before
	public void setUp(){
		Empresa empresa = this.empresaRepository.save(obterEmpresa());
		
		Funcionario funcionario = this.funcionarioRepository.save(obterFuncionario(empresa));
		this.funcionarioId = funcionario.getId();
		
		this.horasRepository.save(obterHoras(funcionario));
		this.horasRepository.save(obterHoras(funcionario));
		this.horasRepository.save(obterHoras(funcionario));

	}
	
	@After
	public final void tearDown(){
		this.empresaRepository.deleteAll();
	}
	
	@Test
	public void buscarFuncionarioPorId(){
		PageRequest page = new PageRequest(0, 10);
		Page<Horas> horas = this.horasRepository.findByFuncionarioId(funcionarioId, page);
		
		assertEquals(3, horas.getTotalElements());
	}	
}
