package com.algaworks.algafood.api.v1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.CozinhaModelAssembler;
import com.algaworks.algafood.api.v1.model.CozinhaDTO;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import com.algaworks.algafood.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RequestMapping(path = "/v1/cozinhas", produces =  MediaType.APPLICATION_JSON_VALUE)
@RestController // tem o responseBody dentro dela
public class CozinhaController implements CozinhaControllerOpenApi {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CozinhaModelAssembler cozinhaModelAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;   
	
	@Autowired
	private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;
	

	@PreAuthorize("isAuthenticated()")
	@Override
	@GetMapping
	public ResponseEntity<PagedModel<CozinhaDTO>>  listar(@PageableDefault(size = 10, sort = "nome") Pageable pageable) {
		
		Page<Cozinha> cozinhasPage = cozinhaRepository.findAll(pageable);
		//pagedResourcesAssembler vai usar o cozinhaModelAssembler para converter o cozinhasPage para cozinhaModelAssembler
		//como de cozinha para cozinhamodel
	    PagedModel<CozinhaDTO> cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhasPage, cozinhaModelAssembler);
	    return ResponseEntity.ok().body(cozinhasPagedModel);
	}	

	@PreAuthorize("isAuthenticated()")
	@Override
	@GetMapping("/{cozinhaId}")
	public CozinhaDTO buscar(@PathVariable Long cozinhaId) {
	    Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
	    
	    return cozinhaModelAssembler.toModel(cozinha);
	}

	@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
	    Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
	    cozinha = cadastroCozinhaService.adicionar(cozinha);
	    
	    return cozinhaModelAssembler.toModel(cozinha);
	}

	@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
	@PutMapping("/{cozinhaId}")
	public CozinhaDTO atualizar(@PathVariable Long cozinhaId,
	        @RequestBody @Valid CozinhaInput cozinhaInput) {
	    Cozinha cozinhaAtual = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
	    cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);
	    cozinhaAtual = cadastroCozinhaService.adicionar(cozinhaAtual);
	    
	    return cozinhaModelAssembler.toModel(cozinhaAtual);
	}
/*
	@DeleteMapping("/{id}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long id) {
		
		try {
			
        cadastroCozinhaService.Excluir(id);
        return ResponseEntity.noContent().build();

		} catch(EntidadeNaoEncontradaException e) {
			  return ResponseEntity.notFound().build();
        
		}catch(EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
*/
	
	@PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT) //se der certo n vai retornana nada
	public void remover(@PathVariable Long id) {
        cadastroCozinhaService.Excluir(id);
	}
}
