package com.algaworks.algafood.core.jackson;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.model.mixina.CidadeMixin;
import com.algaworks.algafood.api.model.mixina.CozinhaMixin;
import com.algaworks.algafood.api.model.mixina.RestauranteMixin;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component 
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;
	
	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		   setMixInAnnotation(Cidade.class, CidadeMixin.class);
		    setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
	}

}
