package com.algaworks.algafood.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AlgaSecurity {
	
	 //pega um objeto authentication que está representando o token da autenticação atual
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	

	public Long getUsuarioId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		return jwt.getClaim("usuario_id"); //igual ao q tem no payload jwt q configuramos
	}
}
