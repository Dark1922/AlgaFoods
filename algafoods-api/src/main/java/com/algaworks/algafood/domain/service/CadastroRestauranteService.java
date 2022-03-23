package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {


	@Autowired 
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroUsuarioService cadastroUsuario;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@Autowired
	private CadastroCidadeService cadastroCidadeService;
	
	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;

	@Transactional //org spring métodos que altera algo no banco de dados
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		Long cidadeId = restaurante.getEndereco().getCidade().getId();
		
		//retorna a cozinha que está dentro do optional  se não retorna nada lança a exceção da entidade não encontrada
		Cozinha cozinha = cadastroCozinhaService.buscarOuFalhar(cozinhaId);
		Cidade cidade = cadastroCidadeService.buscarOuFalhar(cidadeId);
         
	    restaurante.setCozinha(cozinha);	//seta a cozinha se achar o id
		restaurante.getEndereco().setCidade(cidade);
		return restauranteRepository.save(restaurante);
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {
	    return restauranteRepository.findById(restauranteId)
	        .orElseThrow(() -> new RestauranteNaoEncontradaException( restauranteId));
	}
	
	@Transactional  //edponint de ativar um restaurante se tiver desativado
	public void ativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.ativar();
		
	}
	@Transactional  //edponint de inativar um restaurante se tiver desativado
	public void inativar(Long restauranteId) {
		Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
		restauranteAtual.inativar();;
		
	}
	
	     @Transactional
	    public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
	    	Restaurante restaurante = buscarOuFalhar(restauranteId);
	    	
	    	FormaPagamento formaPagamento  = new FormaPagamento();
	    	formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId); //se n existe lança exception 404
	    	 
	    	restaurante.removerFormaPagamento(formaPagamento);
	    }
	     @Transactional
		    public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		    	Restaurante restaurante = buscarOuFalhar(restauranteId);
		    	
		    	FormaPagamento formaPagamento  = new FormaPagamento();
		    	formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId); //se n existe lança exception 404
		    	 
		    	restaurante.adicionarFormaPagamento(formaPagamento);
		    }
	     
	     @Transactional
	     public void abrir(Long restauranteId) {
	         Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
	         
	         restauranteAtual.abrir();
	     }

	     @Transactional
	     public void fechar(Long restauranteId) {
	         Restaurante restauranteAtual = buscarOuFalhar(restauranteId);
	         
	         restauranteAtual.fechar();
	     } 
	     
	     @Transactional
	     public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
	         Restaurante restaurante = buscarOuFalhar(restauranteId);
	         Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
	         
	         restaurante.removerResponsavel(usuario);
	     }

	     @Transactional
	     public void associarResponsavel(Long restauranteId, Long usuarioId) {
	         Restaurante restaurante = buscarOuFalhar(restauranteId);
	         Usuario usuario = cadastroUsuario.buscarOuFalhar(usuarioId);
	         
	         restaurante.adicionarResponsavel(usuario);
	     }
	     
	     @Transactional
	     public void ativar(List<Long> restauranteIds) {
	    	 restauranteIds.forEach(this::ativar); 
	    	 //vai chamar cade elemento que tiver dentro dos ids pra ativar
	    	 //referenciando o métovo de ativar o restaurante acima
	     }
	     @Transactional
	     public void inativar(List<Long> restauranteIds) {
	    	 restauranteIds.forEach(this::inativar); 
	    	 
	     }
}
