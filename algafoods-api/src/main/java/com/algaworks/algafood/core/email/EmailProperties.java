package com.algaworks.algafood.core.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import lombok.Getter;
import lombok.Setter;

@Validated //pro not null funcionar
@Setter
@Getter
@Component
@ConfigurationProperties("algafood.email") //prefixo do application.properties
public class EmailProperties {
	
	private Implementacao impl = Implementacao.FAKE;

	@NotNull //vai ser validado e n pode ser null
	private String remetente; //remetente que vai enviar o e-mail
	
	public enum Implementacao {
	    SMTP, FAKE
	}
}
