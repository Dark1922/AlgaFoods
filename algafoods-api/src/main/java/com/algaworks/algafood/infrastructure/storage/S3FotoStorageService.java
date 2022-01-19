package com.algaworks.algafood.infrastructure.storage;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3FotoStorageService implements FotoStorageService {
	
	@Autowired
	private AmazonS3 amazonS3;
	
	@Autowired
	private StorageProperties storageProperties;

	@Override
	public void armazenar(NovaFoto novaFoto) { 
		try {
			String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());
			var objectMetadata = new ObjectMetadata();//meta dados de um objeto
			objectMetadata.setContentType(novaFoto.getContentType()); //mostrar foto invez de fazer dowload
	
		//submeter para api da amazon que agente ta fazendo uma requisição para colocar um objeto
		var putObjectRequest = new PutObjectRequest(
				storageProperties.getSe().getBucket(),
				caminhoArquivo,
				novaFoto.getInputStream(),
				objectMetadata)
				.withCannedAcl(CannedAccessControlList.PublicRead);
				
		amazonS3.putObject(putObjectRequest);
		}catch(Exception e) {
			throw new StorageException("Não foi possivel enviar Arquivo para Amazon S3", e);
		}
	}

	private String getCaminhoArquivo(String nomeArquivo) {
		return String.format("%s/%s", storageProperties.getSe().getDiretorioFotos(), nomeArquivo);
		//vai formar uma string com / outra string %s/%s , pega o diretorio onde quer por a foto que é o catalogo , e o nome da foto
	}

	@Override
	public void remover(String nomeArquivo) {
		
	}

	@Override
	public InputStream recuperar(String nomeArquivo) {
		return null;
	}

}
