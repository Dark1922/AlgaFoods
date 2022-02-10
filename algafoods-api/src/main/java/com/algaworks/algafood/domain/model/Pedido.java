package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.exception.NegocioException;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)//callSuper = false pra n chamar a classe pai AbstractAggregateRoot
public class Pedido extends AbstractAggregateRoot<Pedido>{

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long id;
	
	private String codigo;
	
	@Column(nullable = false)
	private BigDecimal subtotal;
	
	@Column(nullable = false)
	private BigDecimal taxaFrete;
	
	@Column(nullable = false)
	private BigDecimal valorTotal;
	
	@Column(nullable = false, columnDefinition = "datetime")
	@CreationTimestamp
	private OffsetDateTime dataCriacao;
	
	@Embedded
	private Endereco enderecoEntrega;
	
	private OffsetDateTime dataConfirmacao;
	private OffsetDateTime dataCancelamento;
	private OffsetDateTime dataEntrega;
	
	
	@ManyToOne(fetch = FetchType.LAZY) //nem sempre que agente busca o pedido agente busca o pedido vai precisar do forma pgment
	@JoinColumn(nullable = false)
	private FormaPagamento formaPagamento;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Restaurante restaurante;
	
	@ManyToOne 
	@JoinColumn(name = "usuario_cliente_id", nullable = false)
	private Usuario cliente;
	
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
	private List<ItemPedido> itens = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	private StatusPedido status = StatusPedido.CRIADO;
	
	public void calcularValorTotal() {
	    getItens().forEach(ItemPedido::calcularPrecoTotal);
	    
	    this.subtotal = getItens().stream()
	        .map(item -> item.getPrecoTotal())
	        .reduce(BigDecimal.ZERO, BigDecimal::add);
	    
	    this.valorTotal = this.subtotal.add(this.taxaFrete);
	}

	public void definirFrete() {
	    setTaxaFrete(getRestaurante().getTaxaFrete());
	}

	public void atribuirPedidoAosItens() {
	    getItens().forEach(item -> item.setPedido(this));
	}
	
	public void confirmar() {
		setStatus(StatusPedido.CONFIRMADO);
		setDataConfirmacao(OffsetDateTime.now());
		//registrando um evento que deve ser disparado assim que for salvo no repositorio
		registerEvent(new PedidoConfirmadoEvent(this));//this intancia atual que este método está rodando sendo confirmado
	}
	
	public void entregar() {
		setStatus(StatusPedido.ENTREGUE);
		setDataEntrega(OffsetDateTime.now());
	}
	
	public void cancelar() {
		setStatus(StatusPedido.CANCELADO);
		setDataCancelamento(OffsetDateTime.now());
		registerEvent(new PedidoCanceladoEvent(this));
	}
	
	private void setStatus(StatusPedido novoStatus) {
		if (getStatus().naoPodeAlterarPara(novoStatus)) {
				throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s para %s", 
	                  getCodigo(), getStatus().getDescricao(),
	                  novoStatus.getDescricao()));//pega o id e o status e o que quer mudar
			}
		this.status = novoStatus; //se der certo atribui o status para o novo
		}
	         //método de calback do jpa 
	      @PrePersist //antes de persistir uma entidade pedido antes de inserir algo novo no banco de dados insere esse metodo
	      private void gerarCodigo() {
	    	  setCodigo(UUID.randomUUID().toString());//to string vai pegar os 36 caracteres com especiais uuid do java
	      }
	      
	      public boolean podeSerConfirmado() {
	    	  return getStatus().PodeAlterarPara(StatusPedido.CONFIRMADO);
	      }
	      
	      public boolean podeSerEntregue() {
	    	  return getStatus().PodeAlterarPara(StatusPedido.ENTREGUE);
	      }
	      
	      public boolean podeSerCancelado() {
	    	  return getStatus().PodeAlterarPara(StatusPedido.CANCELADO);
	      }
	}		

