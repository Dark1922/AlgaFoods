package com.algaworks.algafood.api.controller;

import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.input.FotoPordutoInput;

@RestController // tratar o mapeamento do recurso foto
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestaurantePedidoFotoController {

	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,
		@Valid FotoPordutoInput fotoPordutoInput) throws Exception {
		
		//geram um uuid concatenando com o filename
		var nomeArquivo = UUID.randomUUID().toString() + "_" + fotoPordutoInput.getArquivo().getOriginalFilename(); 
		
		var arquivoFoto = Path.of("/Users/DarkJohn/Desktop/catalogo", nomeArquivo); //salva o arquivo nesse path
		
		System.out.println(arquivoFoto);
		System.out.println(fotoPordutoInput.getArquivo().getContentType());
		System.out.println(fotoPordutoInput.getDescricao());
		
		fotoPordutoInput.getArquivo().transferTo(arquivoFoto); //pra onde vai mandar o arquivo foto tranfere
	}
}
