package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
public class CadastroUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private CadastroGrupoService cadastroGrupo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    
    @Transactional
    public Usuario salvar(Usuario usuario) {
    	usuarioRepository.detach(usuario); //para de gerenciar o usuário ai o atualizar usuário vai funcionar sem erro 500    	
    	
    	//ou tá nulo ou tem um usuário existente
    	Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
    	
    	// se o usuario tiver presente,e se n for do proprio usuário caso esteje  atualizando
    	if(usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) { 
    		throw new NegocioException(String.format("Já existe um usuário cadastrado com o e-mail %s", usuario.getEmail()));
    	}
    	
    	if (usuario.isNovo()) {
    		usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
    	}
    	
        return usuarioRepository.save(usuario);
    }
    
    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        
        if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }
        
        usuario.setSenha(passwordEncoder.encode(novaSenha));
    }

    public Usuario buscarOuFalhar(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }    
    
    @Transactional
    public void desassociarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
        
        usuario.removerGrupo(grupo);
    }

    @Transactional
    public void associarGrupo(Long usuarioId, Long grupoId) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        Grupo grupo = cadastroGrupo.buscarOuFalhar(grupoId);
        
        usuario.adicionarGrupo(grupo);

    
    }
}                  
