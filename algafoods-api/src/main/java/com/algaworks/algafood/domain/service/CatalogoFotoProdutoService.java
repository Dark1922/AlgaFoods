package com.algaworks.algafood.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

@Service
public class CatalogoFotoProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository; //agregation root vamos usar o repo do produto pra salvar a foto sem precisar criar outrorepo

	@Transactional
	public FotoProduto salvar(FotoProduto foto) {
		Long restauranteId = foto.getRestauranteId();
		Long ProdutoId = foto.getProduto().getId();
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, ProdutoId);
		 if(fotoExistente.isPresent()) {//tem uma foto dentro desse optional se tiver tem que excluir
			 produtoRepository.delete(fotoExistente.get());
		 }
		return produtoRepository.save(foto);
	}
} 
