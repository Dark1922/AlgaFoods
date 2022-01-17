package com.algaworks.algafood.api.controller;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;

@RestController // tratar o mapeamento do recurso foto
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProduto;
	
	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
		@Valid FotoProdutoInput fotoPordutoInput) throws Exception {
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId); //busca os produtos pra atribuir na foto
		
		MultipartFile arquivo = fotoPordutoInput.getArquivo();
		
		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoPordutoInput.getDescricao()); 
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(UUID.randomUUID() + "_" + arquivo.getOriginalFilename());
		
		FotoProduto fotoSalva = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());
		return fotoProdutoModelAssembler.toModel(fotoSalva);
		
	}
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoModel buscar(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId)  {
	    FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);
	    
	    return fotoProdutoModelAssembler.toModel(fotoProduto);
	}
	@GetMapping //InputStreamResource representa um recurso
	public ResponseEntity<InputStreamResource> servirFoto(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		
		try {
		FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);
		
		MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());  //converter string em um tipo mediatype foto do banco de dados
		List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader); //passa mais de um elemtno image/png,image/jpeg
		//verifica se a foto do banco é compativel com o tipo que estamos passando no header se der match vai trazer ela
		verificarCompatibilidadeMediaType(mediaTypeFoto,mediaTypesAceitas);
	
		InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
		
		return ResponseEntity.ok()
				.contentType(mediaTypeFoto) //adicionar no cabeçalho midiatype sem ser string sem o value
				.body(new InputStreamResource(inputStream));
		}catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build(); 
		}	
	}
	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto,
			List<MediaType> mediaTypesAceita) throws HttpMediaTypeNotAcceptableException {
		boolean compativel = mediaTypesAceita.stream()
				.anyMatch(mediaTypeAceita -> mediaTypeAceita.isCompatibleWith(mediaTypeFoto));
		
		if(!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceita);
		}
	}
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId) {
	    catalogoFotoProduto.excluir(restauranteId, produtoId);
	}
}