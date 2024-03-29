package com.algaworks.algafood.api.v1.controller;

import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> cozinhasPorNome(String nome) {
		return cozinhaRepository.findTodasByNomeContaining(nome);
	}

	@GetMapping("/cozinhas/unica-por-nome")
	public Optional<Cozinha> cozinhaPorNome(String nome) {
		return cozinhaRepository.findByNome(nome);
	}

	@GetMapping("/restaurante/por-taxa-frete")
	public List<Restaurante> restaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.findByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurante/por-nome-frete")
	public List<Restaurante> restaurantesPorNomeFrete(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.find(nome,taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurante/por-nome")
	public List<Restaurante> restaurantesPorNome(String nome, Long cozinhaId) {
		return restauranteRepository.consultarPorNome(nome, cozinhaId);
	}
	
	@GetMapping("/restaurante/primeiro-por-nome")
	public Optional<Restaurante> restaurantesPrimeiroNome(String nome) {
		return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
	}
	
	@GetMapping("/restaurante/top2-por-nome")
	public List<Restaurante>  restaurantesTop2PorNome(String nome) {
		return restauranteRepository.findTop2ByNomeContaining(nome); 
	}  
	
	@GetMapping("/cozinhas/exists")
	public boolean cozinhasExists(String nome) {
		return cozinhaRepository.existsByNome(nome);
	}
	
	@GetMapping("/cozinhas/count-por-cozinha")
	public int cozinhasCountPorCozinha(Long cozinhaId) {
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}
	
	@GetMapping("/restaurante/com-frete-gratis")
	public List<Restaurante> restaurantesComFreteGratis(String nome) {
		return restauranteRepository.findAll(RestauranteSpecs.comFreteGratis().and(comNomeSemelhante(nome)));
	}
	@GetMapping("/restauranete/primeiro")
	public Optional<Restaurante> restaurantePrimeiro() {
		return restauranteRepository.buscarPrimeiro();
	}
	
}
