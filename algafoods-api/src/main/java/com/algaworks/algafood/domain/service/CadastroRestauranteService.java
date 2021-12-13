package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {


	@Autowired 
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@Transactional //org spring métodos que altera algo no banco de dados
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		
		//retorna a cozinha que está dentro do optional  se não retorna nada lança a exceção da entidade não encontrada
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
         
	    restaurante.setCozinha(cozinha);	//seta a cozinha se achar o id
		
		return restauranteRepository.save(restaurante);
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
	    return restauranteRepository.findById(restauranteId)
	        .orElseThrow(() -> new RestauranteNaoEncontradaException( restauranteId));
	}
	
	@Transactional  //edponint de ativar um restaurante se tiver desativado
	public void ativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.ativar();
		
	}
	@Transactional  //edponint de inativar um restaurante se tiver desativado
	public void inativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.inativar();;
		
	}
}
