package com.algaworks.algafood.core.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration	
public class WebConfig implements WebMvcConfigurer {//WebMvcConfigurer métodos de callback para pernsonalizar o spring mvc

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**") //qlq caminho uri url q for chamado vai está habilitado globalmente
		.allowedMethods("*"); //permite todos métodos POST GET HEAD etc caso queira um em especifico só colocar só POST 
		//.allowedOrigins("* astericos para todos ja vem como padrão ou uma url especifica https://algafoods.com.br")
		//.maxAge(30) demora 30 segundos para fazer testes
	}
}
