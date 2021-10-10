package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodsApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

public class BuscaCozinhaMain {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodsApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		 
		CozinhaRepository cadastroCozinhal =  applicationContext.getBean(CozinhaRepository.class);
		
		List<Cozinha> cozinhas = cadastroCozinhal.listar();
		
		for (Cozinha cozinha : cozinhas) {
			System.out.println(cozinha.getNome());
		}
	}
}
