package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido>{
  //elias apelido pro pedido = p faz join o com o cliente que está relacionado ao pedido e faz um fetch
	//busca tb do restaurante
	
	@Query("from Pedido p join fetch p.cliente join fetch p.restaurante r join fetch r.cozinha")
	List<Pedido> findAll();

	//@Query("from Pedido where codigo = :codigo") o spring jpa vai fazer isso automaticamente pela sintaxe findByCodigo
	Optional<Pedido> findByCodigo(String codigo);
	
	
	/*Vamos adicionar a seguinte consulta para saber se o pedido é gerenciado por determinado usuário*/
	@Query("  select case when count(1) > 0 then true else false end"
			+ "  from Pedido ped"
			+ "  join ped.restaurante rest"
			+ "  join rest.responsaveis resp"
			+ "  where ped.codigo = :codigoPedido "
			+ "  and resp.id = :usuarioId")
	boolean isPedidoGerenciadoPor(String codigoPedido, Long usuarioId);
	

}
