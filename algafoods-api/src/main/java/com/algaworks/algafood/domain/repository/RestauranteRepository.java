package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

@Repository
public interface RestauranteRepository 
    extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQuery, JpaSpecificationExecutor<Restaurante> {

	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

	// @Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> consultarPorNome(String nome, @Param("id") Long cozinhaId);

	// List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long
	// cozinha);

	// só 1 é esperado com optional apenas 1 restaurante
	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);

	List<Restaurante> findTop2ByNomeContaining(String nome);
 
	int countByCozinhaId(Long cozinha);
	
	@Query("from Restaurante r join fetch r.cozinha join fetch r.formasPagamento")
	List<Restaurante> findAll();
	
	@Query("select case when count(1) > 0 then true else false end"
			+ "	from Restaurante rest"
			+ "	join rest.responsaveis resp"
			+ "	where rest.id = :restauranteId"
			+ "	and resp.id = :usuarioId")
	boolean existsResponsavel(Long restauranteId, Long usuarioId);

}
