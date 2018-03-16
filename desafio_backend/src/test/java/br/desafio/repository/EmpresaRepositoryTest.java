package br.desafio.repository;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.desafio.entities.Empresa;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest {

	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final String cnpj = "123";
	
	@Before
	public void setUp(){
		Empresa empresa = new Empresa();
		empresa.setRazaoSocial("Exemplo");
		empresa.setCnpj(cnpj);
		this.empresaRepository.save(empresa);
	}
	
	@After
	public final void tearDown(){
		this.empresaRepository.deleteAll();
	}
	
	@Test
	public void buscarPorCnpj(){
		Empresa empresa = this.empresaRepository.findByCnpj(cnpj);
		assertEquals(cnpj, empresa.getCnpj() );
	}

}
