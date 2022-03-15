package com.algaworks.algafood.core.security;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {
    
	public @interface Cozinhas {
		
		@PreAuthorize("@algaSecurity.podeConsultarCozinhas()")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {}
		
		@PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeEditar {}
	}
	
	public @interface Restaurantes { //meta anotação

	    @PreAuthorize("@algaSecurity.podeGerenciarCadastroRestaurantes()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeGerenciarCadastro { }

	    @PreAuthorize("@algaSecurity.podeConsultarRestaurantes()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	    /*bean algaSecurity vem por padrão letra minuscula e seu metodo de renciar com o id do usuário autenticado
	     * e seu restaurante se faz parte dele aquele restaurante ele vai poder gerenciar*/
	    @PreAuthorize("@algaSecurity.podeGerenciarFuncionamentoRestaurantes(#restauranteId)")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeGerenciarFuncionamento { }
	    
	}
	
	public @interface Pedidos { //meta anotação

	    @PreAuthorize("@algaSecurity.podePesquisarPedidos(#filtro.clienteId, #filtro.restauranteId)")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodePesquisar { }

	    @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
	    @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or "
	    		+ "@algaSecurity.usuarioAutenticadoIgual(returnObject.cliente.id) or "
	    		+ "@algaSecurity.gerenciaRestaurante(returnObject.restaurante.id)")  
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeBuscar { }
	    
	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeCriar { }

	    @PreAuthorize("@algaSecurity.podeGerenciarPedidos(#codigoPedido)")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeGerenciarPedidos { }
	
	}
	
	public @interface FormasPagamento {

	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeEditar { }

	    @PreAuthorize("@algaSecurity.podeConsultarFormasPagamento()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
	
	public @interface Cidades {

	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeEditar { }

	    @PreAuthorize("@algaSecurity.podeConsultarCidades()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}

	public @interface Estados {
	    
	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeEditar { }

	    @PreAuthorize("@algaSecurity.podeConsultarEstados()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
	
	public @interface UsuariosGruposPermissoes {

	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
	            + "@algaSecurity.usuarioAutenticadoIgual(#usuarioId)")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeAlterarPropriaSenha { }
	    
	    @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or "
	            + "@algaSecurity.usuarioAutenticadoIgual(#usuarioId))")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeAlterarUsuario { }

	    @PreAuthorize("@algaSecurity.podeEditarUsuariosGruposPermissoes()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeEditar { }
	    

	    @PreAuthorize("@algaSecurity.podeConsultarUsuariosGruposPermissoes()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
	
	public @interface Estatisticas {

	    @PreAuthorize("@algaSecurity.podeConsultarEstatisticas()")
	    @Retention(RUNTIME)
	    @Target(METHOD)
	    public @interface PodeConsultar { }
	    
	}
}
