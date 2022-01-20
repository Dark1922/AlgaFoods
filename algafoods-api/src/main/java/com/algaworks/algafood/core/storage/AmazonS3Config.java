package com.algaworks.algafood.core.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonS3Config {
	
	@Autowired
	private StorageProperties  storageProperties;

	@Bean //método que produz uma instancia de amazonS3, se tornando um bean spring
	public AmazonS3 amazonS3() {
		var credentials = new  BasicAWSCredentials(storageProperties.getS3().getIdChaveAcesso(),
				storageProperties.getS3().getChaveAcessoSecreta()); //chave que representa as credentials de acesso
		
		return AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(credentials)) //passa as credenciais chave de acesso
				.withRegion(storageProperties.getS3().getRegiao()) //região que está ultilizando o serviço amazon
				.build(); //retorna uma instancia de amazon s3 podendo usa-lá com um component spring
	}
}
