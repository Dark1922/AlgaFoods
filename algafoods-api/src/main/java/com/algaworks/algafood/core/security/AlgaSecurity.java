package com.algaworks.algafood.core.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Component
public class AlgaSecurity {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	 //pega um objeto authentication que está representando o token da autenticação atual
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
	

	public Long getUsuarioId() {
		Jwt jwt = (Jwt) getAuthentication().getPrincipal();
		return jwt.getClaim("usuario_id"); //igual ao q tem no payload jwt q configuramos
	}
	
	public boolean gerenciaRestaurante(Long restauranteId) {
		return restauranteRepository.existsResponsavel(getUsuarioId(), restauranteId);
	}
}
