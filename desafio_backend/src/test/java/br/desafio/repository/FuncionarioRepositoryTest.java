package br.desafio.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.desafio.entities.Empresa;
import br.desafio.entities.Funcionario;
import br.desafio.enums.PerfilEnum;
import br.desafio.util.Password;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final String email = "email@exemplo.com";
	private static final String senha = Password.geraBcrypt("123");
	private static final String nome = "Matheus Pereira Exemplo";
	
	private Empresa obterEmpresa(){
		Empresa empresa = new Empresa();
		empresa.setCnpj("123456789");
		empresa.setRazaoSocial("Empresa Exemplo");
		return empresa;
	}
	
	private Funcionario obterFuncionario(Empresa empresa){
		Funcionario funcionario = new Funcionario();
		funcionario.setEmail(email);
		funcionario.setNome(nome);
		funcionario.setSenha(senha);
		funcionario.setEmpresa(empresa);
		funcionario.setPerfil(PerfilEnum.ROLE_USER);
		return funcionario;
	}
	
	@Before
	public void setUp(){
		Empresa empresa = this.empresaRepository.save(obterEmpresa());
		this.funcionarioRepository.save(obterFuncionario(empresa));
	}
	
	@After
	public final void tearDown(){
		this.empresaRepository.deleteAll();
	}
	
	@Test
	public void buscarPorEmail(){
		Funcionario funcionario = this.funcionarioRepository.findByEmail(email);
		assertEquals(email, funcionario.getEmail());
	}
	
	@Test
	public void buscarPorEmailAndSenha(){
		Funcionario funcionario = this.funcionarioRepository.findByEmailAndSenha(email, senha);
		assertNotNull(funcionario);
	}
	
	@Test
	public void buscarPorNome(){
		Funcionario funcionario = this.funcionarioRepository.findByNome(nome);
		assertEquals(nome, funcionario.getNome());
	}


}
