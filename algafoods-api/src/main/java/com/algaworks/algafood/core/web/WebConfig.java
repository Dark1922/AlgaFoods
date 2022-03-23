package com.algaworks.algafood.core.web;

import javax.servlet.Filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration	
public class WebConfig implements WebMvcConfigurer {//WebMvcConfigurer métodos de callback para pernsonalizar o spring mvc

//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**") //qlq caminho uri url q for chamado vai está habilitado globalmente
//		.allowedMethods("*"); //permite todos métodos POST GET HEAD etc caso queira um em especifico só colocar só POST 
		//.allowedOrigins("* astericos para todos ja vem como padrão ou uma url especifica https://algafoods.com.br")
		//.maxAge(30) demora 30 segundos para fazer testes
	//}
	
	/*ao receber uma requisição, na hora que der uma resposta ele gera um hash dessa resposta e coloca o cabeçalho e tag
	 *verifica se o hash dessa resposta coincide  com o Etag que está no if-none-match q está no cabeçalho q vem da requisição
	 *se coincidir ele retorna 304 se n for igual ele continua deixa passar o processamento deixa a resposta chegar no servidor porem
	 *ele adiciona o cabeçalho e tag*/
	@Bean
	public Filter  shallowEtagHeaderFilter() {
		return new ShallowEtagHeaderFilter();
	}
}
