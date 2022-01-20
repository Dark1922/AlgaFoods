package com.algaworks.algafood.domain.service;

import java.io.InputStream;

import lombok.Builder;
import lombok.Getter;

public interface FotoStorageService {

	/* serviço de armazenamento de foto */
	void armazenar(NovaFoto novaFoto);

	void remover(String nomeArquivo);

	FotoRecuperada recuperar(String nomeArquivo); // se tiver url retorna a url se n retorna o inputStream

	default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
		this.armazenar(novaFoto);
		if (nomeArquivoAntigo != null) {
			this.remover(nomeArquivoAntigo);
		}
	}

	@Getter
	@Builder // ajuda a criar o objeto
	class NovaFoto {
		private String nomeArquivo;
		private InputStream inputStream; // getinputstream leitura do arquivo que acabou de fazer upload
		private String contentType;

	}

	@Builder
	@Getter
	class FotoRecuperada {
		private InputStream inputStream;
		private String url;
		
		public boolean temUrl() {
			return url != null;
		}
		
		public boolean temInputStream() {
			return inputStream != null;
		}
	}

}
