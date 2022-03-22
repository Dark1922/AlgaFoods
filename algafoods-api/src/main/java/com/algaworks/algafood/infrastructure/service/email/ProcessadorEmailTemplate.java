package com.algaworks.algafood.infrastructure.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class ProcessadorEmailTemplate {
	
	@Autowired
	private Configuration freemarkerConfig; //fremarker template
	
	protected String processarTemplate(Mensagem mensagem) throws Exception { //usar o template html no corpo
		try {
		Template template = freemarkerConfig.getTemplate(mensagem.getCorpo());
		
		//termplate html e objeto java pra usar objetos java no html
		return FreeMarkerTemplateUtils.processTemplateIntoString(template, mensagem.getVariaveis()); 
		
		}catch(Exception e) {
			throw new EmailException("Não foi posivel montar o template do e-mail", e);
		}
	}

}
