package com.algaworks.algafood.domain.service;

import java.io.InputStream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.FotoStorageService.NovaFoto;

@Service
public class CatalogoFotoProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository; //agregation root vamos usar o repo do produto pra salvar a foto sem precisar criar outrorepo

	@Autowired
	private FotoStorageService fotoStorageService;
	
	@Transactional
	public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
		Long restauranteId = foto.getRestauranteId();
		Long ProdutoId = foto.getProduto().getId();
		Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(restauranteId, ProdutoId);
		 if(fotoExistente.isPresent()) {//tem uma foto dentro desse optional se tiver tem que excluir
			 produtoRepository.delete(fotoExistente.get());
		 }
		 foto = produtoRepository.save(foto); //caso o banco de rolback a foto na vai armazenar tem que ficar antes do armazenamento de foto
		produtoRepository.flush();//descarrega tudo que está na fila insert etc que deia alguma problema antes de armazena a foto
		 NovaFoto novaFoto = NovaFoto.builder()
				 .nomeArquivo(foto.getNomeArquivo())
				 .inputStream(dadosArquivo)
				 .build(); //cria o objeto novaFoto o builder
		 
		 fotoStorageService.armazenar(novaFoto); 
		
		 return foto;
	}
} 
