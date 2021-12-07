package com.algaworks.algafood.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	
	//instancia do model mapper dentro do nosso contexto do spring pra funcionar a injeção dela
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
