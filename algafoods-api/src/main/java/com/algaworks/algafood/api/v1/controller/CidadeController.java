package com.algaworks.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.v1.model.CidadeDTO;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;
import com.algaworks.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.core.ResourceUri.ResourceUriHelper;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;


@RestController
@RequestMapping(path = "/v1/cidades",produces = MediaType.APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenApi{

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastrocidadeService;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@CheckSecurity.Cidades.PodeConsultar
	@GetMapping()
	public CollectionModel<CidadeDTO> listar() {//CollectionModel que tb é um representationaModel
		return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
	}

	@CheckSecurity.Cidades.PodeConsultar
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@PathVariable Long cidadeId) {
		Cidade cidade = cadastrocidadeService.buscarOuFalhar(cidadeId);
	       return cidadeModelAssembler.toModel(cidade);
	}

	
	@CheckSecurity.Cidades.PodeEditar
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(@RequestBody @Valid CidadeInput cidadeInput) {

		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
			
			CidadeDTO cidadeDTO = cidadeModelAssembler.toModel(cadastrocidadeService.salvar(cidade));
			
		    ResourceUriHelper.addUriInResponseHeader(cidadeDTO.getId());
			
			return cidadeDTO;
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	
	@CheckSecurity.Cidades.PodeEditar
	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(@PathVariable Long cidadeId,	@RequestBody @Valid CidadeInput cidadeInput) {
		
		try {
			Cidade cidadeAtual = cadastrocidadeService.buscarOuFalhar(cidadeId);
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			return cidadeModelAssembler.toModel(cadastrocidadeService.salvar(cidadeAtual));

		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@CheckSecurity.Cidades.PodeEditar
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long cidadeId) {
		cadastrocidadeService.excluir(cidadeId);
	}

}
