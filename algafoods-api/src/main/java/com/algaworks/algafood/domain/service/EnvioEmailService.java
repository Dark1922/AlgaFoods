package com.algaworks.algafood.domain.service;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

public interface EnvioEmailService {

	void enviar(Mensagem mensagem); 
	
	@Getter
	@Builder
	class Mensagem {
		@Singular //ele singulariza o destinatarios para destinatario
		private Set<String> destinatarios;
		
		@NonNull //se n lançar eles ao usar o serviço de envio de e-mail vai lançar exception
		private String assunto;
		
		@NonNull
		private String corpo;
		
		@Singular("variavel")
		private Map<String, Object> variaveis; //map é um valor onde a chave é string e objeto
		
		
	}
}
