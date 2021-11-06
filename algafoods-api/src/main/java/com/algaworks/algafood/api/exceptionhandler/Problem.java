package com.algaworks.algafood.api.exceptionhandler;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder //linguagem mais fluente
@JsonInclude(Include.NON_NULL) //só inclua na propriação json se não for nulo
public class Problem {

	private Integer status;
    private String type;
    private String title;
    private String detail;
    private String userMessage;
    private List<Field> fields;
    
    @Getter
    @Builder
    public static class Field {
    	private String name;
    	private String userMessage;
    }
	
	@JsonFormat(timezone = "GMT-3",pattern = "yyyy-MM-dd hh:mm:ss")
	private LocalDateTime timestamp; // do acontecimento do erro
	
}
