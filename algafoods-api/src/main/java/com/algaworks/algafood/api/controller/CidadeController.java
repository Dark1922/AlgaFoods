package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.CidadeModelAssembler;
import com.algaworks.algafood.api.model.CidadeDTO;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastrocidadeService;

	@Autowired
	private CidadeModelAssembler cidadeModelAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;

	@ApiOperation("Lista de Cidades")
	@GetMapping()
	public List<CidadeDTO> listar() {
		return cidadeModelAssembler.toCollectionModel(cidadeRepository.findAll());
	}

	@ApiOperation("Buscar uma cidade por ID")
	@GetMapping("/{cidadeId}")
	public CidadeDTO buscar(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long cidadeId) {
		Cidade cidade = cadastrocidadeService.buscarOuFalhar(cidadeId);
		return cidadeModelAssembler.toModel(cidade);
	}

	@ApiOperation("Cadastra uma Cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO adicionar(
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade com os novos dados") 
			@RequestBody @Valid CidadeInput cidadeInput) {

		try {
			Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
			return cidadeModelAssembler.toModel(cadastrocidadeService.salvar(cidade));
		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation("Atualiza uma cidade por ID")
	@PutMapping("/{cidadeId}")
	public CidadeDTO atualizar(
			@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long cidadeId,
			@ApiParam(name = "corpo", value = "Representação de uma nova cidade com os novos dados") 
			@RequestBody @Valid CidadeInput cidadeInput) {

		try {
			Cidade cidadeAtual = cadastrocidadeService.buscarOuFalhar(cidadeId);
			cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

			return cidadeModelAssembler.toModel(cadastrocidadeService.salvar(cidadeAtual));

		} catch (EstadoNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@ApiOperation("Exclui uma cidade por ID")
	@DeleteMapping("/{cidadeId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long cidadeId) {
		cadastrocidadeService.excluir(cidadeId);
	}

}
