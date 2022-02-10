package com.algaworks.algafood.api.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.model.UsuarioDTO;
import com.algaworks.algafood.core.linkhateos.AlgaLinks;
import com.algaworks.algafood.domain.model.Usuario;

@Component
public class UsuarioModelAssembler  extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private AlgaLinks algaLinks;
    
    public UsuarioModelAssembler() {
        super(UsuarioController.class, UsuarioDTO.class);
    }
    
    @Override
    public UsuarioDTO toModel(Usuario usuario) {
    	
        UsuarioDTO usuarioModel = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, usuarioModel);
        
        usuarioModel.add(algaLinks.linkToUsuarios("usuarios"));
        
        usuarioModel.add(algaLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
        
        return usuarioModel;
    }
    
    @Override
    public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
            .add(algaLinks.linkToUsuarios());
    }   
}        
