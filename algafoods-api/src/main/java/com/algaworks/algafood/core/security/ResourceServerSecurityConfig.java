package com.algaworks.algafood.core.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity //ativa a segurança e tem o configuration tb
public class ResourceServerSecurityConfig extends WebSecurityConfigurerAdapter {
	
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		  .anyRequest().authenticated() //autoriza requisição qualquer requisição autenticada
		  .and()
		  .oauth2ResourceServer().opaqueToken(); //configurando um resource server
	}
	
	
}
