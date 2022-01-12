package com.algaworks.algafood.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile>{
	
    private DataSize maxSize; //representa um tamanho de dados , classe do spring
	
	@Override
	public boolean isValid(MultipartFile value, ConstraintValidatorContext context) {
		//multipartfile retorna em bites ent se ele receber um arquivo getsize tamanho em bytes compara ele com o maxsize em bytes
		//estando menor ou igual vai está tudo certo., transforma ele em bytes pra fazer a comparação se tiver na condiçãoo retorna true 
		//está valido
		 return value == null || value.getSize() <= this.maxSize.toBytes();
	}
	
	@Override
	public void initialize(FileSize constraintAnnotation) {
		//vai fazer um parse da string para datasize
		this.maxSize = DataSize.parse(constraintAnnotation.max());
	}

}
