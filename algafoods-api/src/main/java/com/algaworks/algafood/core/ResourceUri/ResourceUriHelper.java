package com.algaworks.algafood.core.ResourceUri;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.experimental.UtilityClass;

@UtilityClass //uma classe q n pode ser extendida  classe ultilitária
public class ResourceUriHelper {
	
    public static void addUriInResponseHeader(Object resourceId) {//adicionar uri no cabeçalho da resposta
    	
    	URI uri =	ServletUriComponentsBuilder.fromCurrentRequestUri()
    			.path("/{id}")//pega a uri de cidade e apartir dessa uri pega o id nessa url
    			.buildAndExpand(resourceId).toUri();//pega o id que essa requisição gerou de criar a cidade
    		
    		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
    				.getResponse();
    		
    		response.setHeader(HttpHeaders.LOCATION, uri.toString()); //adiciona a uri completa no headers
    }
}
