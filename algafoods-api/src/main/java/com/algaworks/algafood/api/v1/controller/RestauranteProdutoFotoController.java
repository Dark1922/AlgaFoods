package com.algaworks.algafood.api.v1.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algaworks.algafood.api.v1.assembler.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.algaworks.algafood.domain.service.FotoStorageService.FotoRecuperada;

@RestController // tratar o mapeamento do recurso foto
@RequestMapping(path = "/v1/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces =  MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {
 
	@Autowired
	private CadastroProdutoService cadastroProdutoService;
	
	@Autowired
	private CatalogoFotoProdutoService catalogoFotoProduto;
	
	@Autowired
	private FotoProdutoModelAssembler fotoProdutoModelAssembler;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@CheckSecurity.Restaurantes.PodeEditar
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoModel atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
		@Valid FotoProdutoInput fotoPordutoInput, 
		@RequestPart(required = true) MultipartFile arquivo) throws Exception {
		Produto produto = cadastroProdutoService.buscarOuFalhar(restauranteId, produtoId); //busca os produtos pra atribuir na foto
		
		 arquivo = fotoPordutoInput.getArquivo();
		
		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoPordutoInput.getDescricao()); 
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(UUID.randomUUID() + "_" + arquivo.getOriginalFilename());
		
		FotoProduto fotoSalva = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());
		return fotoProdutoModelAssembler.toModel(fotoSalva);
		
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@GetMapping
	public FotoProdutoModel buscar(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId)  {
	    FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);
	    
	    return fotoProdutoModelAssembler.toModel(fotoProduto);
	}
	
	@GetMapping(produces = MediaType.ALL_VALUE) //InputStreamResource representa um recurso
	public ResponseEntity<?> servirFoto(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		
		try {
		FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(restauranteId, produtoId);
		
		MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());  //converter string em um tipo mediatype foto do banco de dados
		List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader); //passa mais de um elemtno image/png,image/jpeg
		//verifica se a foto do banco é compativel com o tipo que estamos passando no header se der match vai trazer ela
		verificarCompatibilidadeMediaType(mediaTypeFoto,mediaTypesAceitas);
	
		FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
		
		if(fotoRecuperada.temUrl()) {
			return ResponseEntity.status(HttpStatus.FOUND)
					.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
					.build();
		}else {
			return ResponseEntity.ok()
				.contentType(mediaTypeFoto) //adicionar no cabeçalho midiatype sem ser string sem o value
				.body(new InputStreamResource(fotoRecuperada.getInputStream()));
		}
		
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
	
	@CheckSecurity.Restaurantes.PodeEditar
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId) {
	    catalogoFotoProduto.excluir(restauranteId, produtoId);
	}
}
