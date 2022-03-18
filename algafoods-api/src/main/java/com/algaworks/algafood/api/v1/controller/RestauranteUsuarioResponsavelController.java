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

import com.algaworks.algafood.api.v1.assembler.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.model.UsuarioDTO;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafood.core.linkhateos.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;



@RestController
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi{

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;
    
    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;
    
    @Autowired
    private AlgaLinks algaLinks;
    
    @Autowired
    private AlgaSecurity algaSecurity;  
    
    @GetMapping
    public CollectionModel<UsuarioDTO>  listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);
        
        CollectionModel<UsuarioDTO> usuariosDTO = usuarioModelAssembler.toCollectionModel(restaurante.getResponsaveis())
        		.removeLinks()
        		.add(algaLinks.linkToResponsaveisRestaurante(restauranteId));//passa link com id do restaurante responsavel
        		
         if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
        		usuariosDTO.add(algaLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));
        
        usuariosDTO.getContent().stream().forEach(usuarioDTO -> {
        	usuarioDTO.add(algaLinks.linkToRestauranteResponsavelDesassociacao(
                    restauranteId, usuarioDTO.getId(), "desassociar"));
        });
        		 }
        
        return usuariosDTO;
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