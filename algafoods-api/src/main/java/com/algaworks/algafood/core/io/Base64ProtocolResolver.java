package com.algaworks.algafood.core.io;

import java.util.Base64;

import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ProtocolResolver;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

// extendendo uma funcionaldiade do sprigng para o base 64
//ApplicationListener alguem fica observado eventos da aplicação e reage a determinado evento
public class Base64ProtocolResolver implements ProtocolResolver,
         ApplicationListener<ApplicationContextInitializedEvent> {
      
	
	@Override
	public Resource resolve(String location, ResourceLoader resourceLoader) {
		if(location.startsWith("base64:")) {
			//vai pegar a string inteira dps dos dois pontos do binario apartir da posição 7
			//posição 7 pq base64: vai ser apartir dos dois pontos que é a 7 posição dessa string
			//vai transforma a string em um array de bytes já do binario codificado já do jwks
		  byte[] decodeResource =	Base64.getDecoder().decode(location.substring(7));
		  return new ByteArrayResource(decodeResource);
		}
		return null;
	}

	@Override
	public void onApplicationEvent(ApplicationContextInitializedEvent event) {
		event.getApplicationContext().addProtocolResolver(this);
		//getApplicationContext que ele acabou de inicializar adicionar no contextor um protocol resolver
		//o this que é a própria instacia
	}

}
