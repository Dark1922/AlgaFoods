package com.algaworks.algafood.infrastructure.storage;

import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.algaworks.algafood.domain.service.FotoStorageService;

@Service
public class LocalFotoStorageService implements FotoStorageService {

	@Value("${algafood.storage.local.diretorio-fotos}") //passa propriedade do application properties
	private Path diretorioFotos;

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
    	  return diretorioFotos.resolve(Path.of(nomeArquivo));
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
}
