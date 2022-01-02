package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJacksonValue;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.core.validation.ValidacaoException;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

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
	
//    @GetMapping
//	public MappingJacksonValue listar(@RequestParam(required = false) String projecao) {
//    	List<Restaurante> restaurantes = restauranteRepository.findAll();
//    	List<RestauranteDTO> restaurantesDTO = restauranteModelAssembler.toCollectionModel(restaurantes);
//    	
//    	MappingJacksonValue restaurantesWrapper = new MappingJacksonValue(restaurantesDTO);
//    	restaurantesWrapper.setSerializationView(RestauranteView.Resumo.class);
//    
//    	if("apenas-nome".equals(projecao)) {
//    	restaurantesWrapper.setSerializationView(RestauranteView.ApenasNome.class);
//    	}else if ("completo".equals(projecao)) {
//    		restaurantesWrapper.setSerializationView(null);
//    	}	
//    	
//    	
//    	return restaurantesWrapper;
//	}
	
    
	@JsonView(RestauranteView.Resumo.class)
    @GetMapping
   	public List<RestauranteDTO> listar() {
   	return restauranteModelAssembler.toCollectionModel(restauranteRepository.findAll());
   	}
    
	@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	public List<RestauranteDTO> listarResumido() {
		return listar();
	}

	

	@GetMapping("/{restauranteId}")
	public RestauranteDTO buscar(@PathVariable Long restauranteId) {
		Restaurante restaurante = cadastroRestauranteService.buscarOuFalhar(restauranteId);
		
		
		return restauranteModelAssembler.toModel(restaurante);
	}



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

	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@PathVariable Long restauranteId, @RequestBody @Valid RestauranteInput restauranteInput) {
		
		try {
			//Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput); 
			Restaurante restauranteAtual = cadastroRestauranteService.buscarOuFalhar(restauranteId);

        	restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);
//			BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro",
//					"produtos");
			return restauranteModelAssembler.toModel(cadastroRestauranteService.salvar(restauranteAtual));
		} catch (CozinhaNaoEncontradaException|CidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	

	
	@PutMapping("/blabla")
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
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/{restauranteId}/ativar")
     public void ativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.ativar(restauranteId);
    	 
     }
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{restauranteId}/inativar")
     public void inativar(@PathVariable Long restauranteId) {
		cadastroRestauranteService.inativar(restauranteId);
    	 
     }
	
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId) {
	    cadastroRestauranteService.abrir(restauranteId);
	}

	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId) {
	    cadastroRestauranteService.fechar(restauranteId);
	} 
	
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMultiplosRestaurantes(@RequestBody List<Long> restauranteIds) {
		try {
		cadastroRestauranteService.ativar(restauranteIds);
		}catch(RestauranteNaoEncontradaException e) {//vai retornar codigo de 400 como padrão
			throw new NegocioException(e.getMessage(), e); //msg e a causa relança como negocioexception o restanaoent
		}
	}
	@DeleteMapping("/inativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMultiplosRestaurantes(@RequestBody List<Long> restauranteIds) {
		try {
		cadastroRestauranteService.inativar(restauranteIds);
		}catch(RestauranteNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
}
