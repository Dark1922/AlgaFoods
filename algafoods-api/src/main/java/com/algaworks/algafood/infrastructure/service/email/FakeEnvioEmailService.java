package com.algaworks.algafood.infrastructure.service.email;


import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.domain.service.EnvioEmailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService implements EnvioEmailService {

	@Autowired
	private ProcessadorEmailTemplate processadorEmailTemplate;
	
    @Override
    public void enviar(Mensagem mensagem)  {
    	try {
        // Foi necessário alterar o modificador de acesso do método processarTemplate
        // da classe pai para "protected", para poder chamar aqui
        String corpo = processadorEmailTemplate.processarTemplate(mensagem);
        
        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    }
} 