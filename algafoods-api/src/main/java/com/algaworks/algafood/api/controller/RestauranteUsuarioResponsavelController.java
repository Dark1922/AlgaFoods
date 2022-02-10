package com.algaworks.algafood.api.controller;

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

import com.algaworks.algafood.api.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafood.core.linkhateos.AlgaLinks;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;



@RestController
@RequestMapping(path = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi{

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;
    
    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;
    
    @Autowired
    private AlgaLinks algaLinks;
    
    @GetMapping
    public ResponseEntity<CollectionModel<UsuarioDTO>>  listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
        
        CollectionModel<UsuarioDTO> usuariosDTO = usuarioModelAssembler.toCollectionModel(restaurante.getResponsaveis())
        		.removeLinks()
        		.add(algaLinks.linkToResponsaveisRestaurante(restauranteId))//passa link com id do restaurante responsavel
        		.add(algaLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));
        
        usuariosDTO.getContent().stream().forEach(usuarioDTO -> {
        	usuarioDTO.add(algaLinks.linkToRestauranteResponsavelDesassociacao(
                    restauranteId, usuarioDTO.getId(), "desassociar"));
        });
        
        return ResponseEntity.ok().body(usuariosDTO);
    }
    
    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.desassociarResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{usuarioId}")
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
        cadastroRestaurante.associarResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }
}