package com.algaworks.algafood.jpa;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.algaworks.algafood.AlgafoodsApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;

public class ExclusaoCozinhaMain {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodsApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		cadastroCozinha cadastroCozinhal = ((BeanFactory) applicationContext).getBean(cadastroCozinha.class);
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(1l);
        		
		
		cadastroCozinhal.remover(cozinha);
		
	}
}
