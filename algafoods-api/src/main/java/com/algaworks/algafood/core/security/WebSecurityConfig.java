package com.algaworks.algafood.core.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity //ativa a segurança e tem o configuration tb
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
		  .withUser("admin")
		  .password(passwordEncoder().encode("admin"))
		  .roles("ADMIN");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic()
		.and()
		.authorizeRequests()
		  .antMatchers("/v1/cozinhas/**").permitAll() //permite tudo antes de restringir autenticated
		  .anyRequest().authenticated() //autoriza requisição qualquer requisição autenticada
		  
		  .and()
		    .sessionManagement()
		      .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //n vai usar sessions tira os cookies
		      
		      .and()
		         .csrf().disable(); //só precisa qnd usa cookie ent desativa pq vamos passas as credencias e usar auth02
	}
	
	@Bean  //criptografando a senha usando bcrypt
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
      
	
}
