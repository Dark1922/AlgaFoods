package com.algaworks.algafood.domain.service;

import java.io.InputStream;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {
	
	                                          /*serviço de armazenamento de foto*/
  void armazenar(NovaFoto novaFoto); 
  
  @Getter
  @Builder //ajuda a criar o objeto
  class NovaFoto {
	  private String nomeArquivo;
	  private InputStream inputStream;  //getinputstream leitura do arquivo que acabou de fazer upload
	
  }
}
