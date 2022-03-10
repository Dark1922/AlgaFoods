package com.algaworks.algafood.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.v1.model.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.algaworks.algafood.core.linkhateos.AlgaLinks;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {


	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;
	
	@Autowired
	private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
	
	@Autowired
	private AlgaLinks algaLinks;

	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping()
	public CollectionModel<FormaPagamentoDTO> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId); 
		
		CollectionModel<FormaPagamentoDTO> formasPagamentoDTO
		= formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPagamento())
			.removeLinks()
			.add(algaLinks.linkToRestauranteFormasPagamento(restauranteId))
			.add(algaLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));
	
		formasPagamentoDTO.getContent().forEach(formaPagamentoDTO -> {
		formaPagamentoDTO.add(algaLinks.linkToRestauranteFormaPagamentoDesassociacao(
				restauranteId, formaPagamentoDTO.getId(), "desassociar"));
	});
	
	return formasPagamentoDTO;
	} 
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@DeleteMapping("/{formaPagamentoId}")
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
       cadastroRestauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
      return ResponseEntity.noContent().build();
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@PutMapping("/{formaPagamentoId}")
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
       cadastroRestauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
       return ResponseEntity.noContent().build();
	}

	
	
	
}
