package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.PermissaoDTO;
import com.algaworks.algafood.api.openapi.controller.PermissaoControllerOpenApi;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@RestController
@RequestMapping(path = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

	   @Autowired
	    private PermissaoRepository permissaoRepository;
	    
	    @Autowired
	    private PermissaoModelAssembler permissaoModelAssembler;
	    
	    @Override
	    @GetMapping
	    public ResponseEntity<CollectionModel<PermissaoDTO>>  listar() {
	        List<Permissao> todasPermissoes = permissaoRepository.findAll();
	       return ResponseEntity.ok().body(permissaoModelAssembler.toCollectionModel(todasPermissoes));
	    } 
}
