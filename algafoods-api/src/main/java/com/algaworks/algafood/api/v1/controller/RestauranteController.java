package com.algaworks.algafood.api.v1.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.RestauranteApenasNomeModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteBasicModelAssembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteDTO;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/v1/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi{

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestauranteService;

	@Autowired
	private SmartValidator validator;
	
	@Autowired
	private RestauranteModelAssembler restauranteModelAssembler;
	
	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;
	
	@Autowired
	private RestauranteBasicModelAssembler restauranteBasicModelAssembler;

	@Autowired
	private RestauranteApenasNomeModelAssembler restauranteApenasNomeModelAssembler; 
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
    @GetMapping
    public CollectionModel<RestauranteBasicoModel> listar() {
        return restauranteBasicModelAssembler
                .toCollectionModel(restauranteRepository.findAll());
    }
    
	@CheckSecurity.Restaurantes.PodeConsultar
	   @Override
	    @GetMapping(params = "projecao=apenas-nome")
	    public CollectionModel<RestauranteApenasNomeModel> listarApenasNomes() {
	        return restauranteApenasNomeModelAssembler
	                .toCollectionModel(restauranteRepository.findAll());
	    }

	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		return restauranteModelAssembler.toModel(restaurante);
	}


	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		try {

			Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput); //convertendo o restaurante pros dados de entrada input
			
			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restaurante)); //converter pro modelo de representação da dessa model

		} catch (CozinhaNaoEncontradaException e) {

			throw new NegocioException(e.getMessage(), e);
		}
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput) {
		
		try {
			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        	restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
        	
			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException|CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@ApiOperation(value = "Configuração do Path", hidden = true)
	@PutMapping("/restaurantePath")
	public RestauranteDTO atttPatch(@PathVariable Long restauranteId, @RequestBody @Valid Restaurante restaurante) {
		
		try {
			
			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);

			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro",
					"produtos");
			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e); 
		}
	}

	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PatchMapping("/{restauranteId}")
	public RestauranteDTO atualizarParcial( @PathVariable Long restauranteId, @RequestBody @Valid Map<String, Object> campos,
			HttpServletRequest request) {
		
		
		Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);

		merge(campos, restauranteAtual, request);
		
		validate(restauranteAtual, "restaurante");

		return atttPatch(restauranteId, restauranteAtual);
	}

	private void validate(Restaurante restaurante, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante,objectName);
		validator.validate(restaurante, bindingResult);
		
		if(bindingResult.hasErrors()) { //se tem error ai dentro do restaurante que estamos atualizando algo
			throw new  ValidacaoException(bindingResult);
		}
	} 

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino, HttpServletRequest request) {
        
		//passamos o request que recebemos no método a requisição por parametro*/
		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
		
		try { 
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
		
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = org.springframework.util.ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true); // torna a variavel acessivel nome taxa_frete por eles serem privados

			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem); // buscando valor do campo

			System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);

			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
		}catch(IllegalArgumentException e) {
			Throwable rootCause = ExceptionUtils.getRootCause(e); //pegando a causa do erro
		throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{restauranteId}/ativar")
     public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.ativar(restauranteId);
		return ResponseEntity.noContent().build();
     }
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@Override
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{restauranteId}/inativar")
     public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.inativar(restauranteId);
		 return ResponseEntity.noContent().build();
     }
	
	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
	    cadastroRestauranteService.abrir(restauranteId);
	    return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
	@Override
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
	    cadastroRestauranteService.fechar(restauranteId);
	    return ResponseEntity.noContent().build();
	} 
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativarMultiplosRestaurantes(@RequestBody List<Long> restauranteIds) {
		try {
		cadastroRestauranteService.ativar(restauranteIds);
		return ResponseEntity.noContent().build();
		}catch(RestauranteNaoEncontradaException e) {//vai retornar codigo de 400 como padrão
			throw new NegocioException(e.getMessage(), e); //msg e a causa relança como negocioexception o restanaoent
		}
	}
	
	@CheckSecurity.Restaurantes.PodeGerenciarCadastro
	@DeleteMapping("/inativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativarMultiplosRestaurantes(@RequestBody List<Long> restauranteIds) {
		try {
		cadastroRestauranteService.inativar(restauranteIds);
		return ResponseEntity.noContent().build();
		}catch(RestauranteNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
}
