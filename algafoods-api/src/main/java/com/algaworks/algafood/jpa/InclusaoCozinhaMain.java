package com.algaworks.algafood.jpa;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodsApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;

public class InclusaoCozinhaMain {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodsApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		cadastroCozinha cadastroCozinhal = ((BeanFactory) applicationContext).getBean(cadastroCozinha.class);
		
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("Brasileira");
		
		Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Japonesa");
		
		cozinha = cadastroCozinhal.adiciona(cozinha);
		cozinha1 = cadastroCozinhal.adiciona(cozinha1);
		System.out.println(cozinha.getNome() + "\n" + cozinha1.getNome());
		
	}
}
