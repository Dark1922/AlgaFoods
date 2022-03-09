package com.algaworks.algafood.core.security;

import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
			.cors().and()
			.oauth2ResourceServer().jwt()
			.jwtAuthenticationConverter(jwtAuthenticationConverter());
	}
	
	private JwtAuthenticationConverter jwtAuthenticationConverter() {
		var jwtAuthenticationConverter = new JwtAuthenticationConverter();
		
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
		    var authorities = jwt.getClaimAsStringList("authorities"); //lista de string de authorities
		    if(authorities == null) {
		    	authorities = Collections.emptyList();
		    }
		    return authorities.stream() //convertendo para uma lista de SimpleGrantedAuthority com uma string dentro dela
		    		.map(SimpleGrantedAuthority::new)
		    		.collect(Collectors.toList());
		});
		return jwtAuthenticationConverter;
	}
}	