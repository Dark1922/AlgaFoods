package com.algaworks.algafood.core.security;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@EnableWebSecurity //ativa a segurança e tem o configuration tb
public class ResourceServerSecurityConfig extends WebSecurityConfigurerAdapter {
	
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		  .anyRequest().authenticated() //autoriza requisição qualquer requisição autenticada
		  .and()
		  .cors().and() //libera o cors
		  .oauth2ResourceServer().jwt(); //configurando um resource server
	}
	 
	@Bean
	public JwtDecoder jwtDecoder() {
		var secretKey = new SecretKeySpec("DB4AEF4719809709E560ED8DE2F9C77B886B963B28BA20E9A8A621BBD4ABA400".getBytes(),
				"HMACSHA256");//algoritimo e chave secreta
		return NimbusJwtDecoder.withSecretKey(secretKey).build();//passa os dados do secretkey
	}
	 
	
}
