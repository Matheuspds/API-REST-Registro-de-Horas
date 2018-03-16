package br.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import br.desafio.security.TokenFilter;

@SpringBootApplication
@EnableCaching
public class DesafioBackendApplication {

	
	
	@Bean
	public FilterRegistrationBean filtroJwt() {
		FilterRegistrationBean frb = new FilterRegistrationBean();
		frb.setFilter(new TokenFilter());
		frb.addUrlPatterns("/admin/*");
		
		return frb;
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(DesafioBackendApplication.class, args);
	}
}
