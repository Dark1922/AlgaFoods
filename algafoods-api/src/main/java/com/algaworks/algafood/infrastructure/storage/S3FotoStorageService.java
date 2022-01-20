package com.algaworks.algafood.infrastructure.storage;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

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
				storageProperties.getS3().getBucket(),
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
		return String.format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
		//vai formar uma string com / outra string %s/%s , pega o diretorio onde quer por a foto que é o catalogo , e o nome da foto
	}

	@Override
	public void remover(String nomeArquivo) {
	    try {
	        String caminhoArquivo = getCaminhoArquivo(nomeArquivo);

	        var deleteObjectRequest = new DeleteObjectRequest(
	                storageProperties.getS3().getBucket(), caminhoArquivo);

	        amazonS3.deleteObject(deleteObjectRequest);
	    } catch (Exception e) {
	        throw new StorageException("Não foi possível excluir arquivo na Amazon S3.", e);
	    }
	}

	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
		String caminhoArquivo = getCaminhoArquivo(nomeArquivo);
	    URL url = amazonS3.getUrl(storageProperties.getS3().getBucket(), caminhoArquivo); //monta url desse arquivo pelo caminho na amazons
	    return FotoRecuperada.builder()
	    		.url(url.toString()).build(); //transforma em uma string a url e retorna a foto recuperada
	}

}
