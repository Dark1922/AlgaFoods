package com.algaworks.algafood.api.v1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.v1.assembler.PermissaoModelAssembler;
import com.algaworks.algafood.api.v1.model.PermissaoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.PermissaoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

@RestController
@RequestMapping(path = "/v1/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

	   @Autowired
	    private PermissaoRepository permissaoRepository;
	    
	    @Autowired
	    private PermissaoModelAssembler permissaoModelAssembler;
	    
	    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	    @Override
	    @GetMapping
	    public ResponseEntity<CollectionModel<PermissaoDTO>>  listar() {
	        List<Permissao> todasPermissoes = permissaoRepository.findAll();
	       return ResponseEntity.ok().body(permissaoModelAssembler.toCollectionModel(todasPermissoes));
	    } 
}
