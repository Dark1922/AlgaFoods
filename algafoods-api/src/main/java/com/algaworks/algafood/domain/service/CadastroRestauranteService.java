package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	private static final String MSG_RESTAURANTE_NAO_ENCONTRADO  = "Não existe cadastro de restaurante com codigo %d";

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		
		//retorna a cozinha que está dentro do optional  se não retorna nada lança a exceção da entidade não encontrada
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
         
	    restaurante.setCozinha(cozinha);	//seta a cozinha se achar o id
		
		return restauranteRepository.save(restaurante);
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
	    return restauranteRepository.findById(restauranteId)
	        .orElseThrow(() -> new EntidadeNaoEncontradaException(
	                String.format(MSG_RESTAURANTE_NAO_ENCONTRADO , restauranteId)));
	}
}
