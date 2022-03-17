package com.algaworks.algafood;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.algaworks.algafood.core.io.Base64ProtocolResolver;
import com.algaworks.algafood.infrastructure.repository.CustomJpaRepositoryImpl;
 
@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgafoodsApiApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		var app = new SpringApplication(AlgafoodsApiApplication.class); 
		//antes de roda a aplicação adiciona o listener
		app.addListeners(new Base64ProtocolResolver());
		app.run(args);
		
		//SpringApplication.run(AlgafoodsApiApplication.class, args);
	}

}
 