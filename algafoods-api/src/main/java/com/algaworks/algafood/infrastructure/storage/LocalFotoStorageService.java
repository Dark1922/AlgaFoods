package com.algaworks.algafood.infrastructure.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.core.storage.StorageProperties;
import com.algaworks.algafood.domain.service.FotoStorageService;

public class LocalFotoStorageService implements FotoStorageService {

	@Autowired
	private StorageProperties storageProperties;

	@Override
	public void armazenar(NovaFoto novaFoto)  {
		
      Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());//caminho das novaFoto onde quer armazenar
      try {
    	  //copiamos os dados que estamos recebendo da foto para o caminho do armazenamento arquivoPath
		FileCopyUtils.copy(novaFoto.getInputStream(), Files.newOutputStream(arquivoPath));
	} catch (Exception e) {
		throw new  StorageException("Não foi possivel armazenar arquivo.",e);
	}
	}
      private Path getArquivoPath(String nomeArquivo) { //pega o diretorio aonde vai armazenar mais o arquivo
    	  return storageProperties.getLocal().getDiretorioFotos()
    			  .resolve(nomeArquivo);
      } 
      
	@Override
	public void remover(String nomeArquivo) {
		try {
         Path arquivoPath = getArquivoPath(nomeArquivo);
         Files.deleteIfExists(arquivoPath);
		}catch (Exception e) {
     		throw new  StorageException("Não foi possivel excluir arquivo.",e);
     	}
	}
	
	@Override
	public FotoRecuperada recuperar(String nomeArquivo) {
	    try {
	        Path arquivoPath = getArquivoPath(nomeArquivo);

	        FotoRecuperada fotoRecuperada = FotoRecuperada.builder()
	        		.inputStream(Files.newInputStream(arquivoPath))
                    .build();	        		
	        
	        return  fotoRecuperada;
	    } catch (Exception e) {
	        throw new StorageException("Não foi possível recuperar arquivo.", e);
	    }
	}       
}
