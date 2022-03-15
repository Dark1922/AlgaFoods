package com.algaworks.algafood.api.v1.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.model.RestauranteDTO;
import com.algaworks.algafood.core.linkhateos.AlgaLinks;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Restaurante;

@Component
public class RestauranteModelAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private AlgaLinks algaLinks;

	@Autowired
	private AlgaSecurity algaSecurity;

	public RestauranteModelAssembler() {
		super(RestauranteController.class, RestauranteDTO.class);
	}

	@Override
	public RestauranteDTO toModel(Restaurante restaurante) {
		RestauranteDTO restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteModel);

		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToRestaurantes("restaurantes"));
		}

		restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));

		restauranteModel.getEndereco().getCidade()
				.add(algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));

		restauranteModel.add(algaLinks.linkToRestauranteFormasPagamento(restaurante.getId(), "formas-pagamento"));

		restauranteModel.add(algaLinks.linkToResponsaveisRestaurante(restaurante.getId(), "responsaveis"));

		if (algaSecurity.podeGerenciarCadastroRestaurantes()) {
			if (restaurante.ativacaoPermitida()) {
				restauranteModel.add(algaLinks.linkToRestauranteAtivacao(restaurante.getId(), "ativar"));
			}

			if (restaurante.inativacaoPermitida()) {
				restauranteModel.add(algaLinks.linkToRestauranteInativacao(restaurante.getId(), "inativar"));
			}
		}
		if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(restaurante.getId())) {
			if (restaurante.aberturaPermitida()) {
				restauranteModel.add(algaLinks.linkToRestauranteAbertura(restaurante.getId(), "abrir"));
			}
			if (restaurante.fechamentoPermitido()) {
				restauranteModel.add(algaLinks.linkToRestauranteFechamento(restaurante.getId(), "fechar"));
			}
		}
		if (algaSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(algaLinks.linkToProdutos(restaurante.getId(), "produtos"));
		}
		if (algaSecurity.podeConsultarCozinhas()) {
			restauranteModel.getCozinha().add(algaLinks.linkToCozinha(restaurante.getCozinha().getId()));
		}
		if (algaSecurity.podeConsultarCidades()) {
			if (restauranteModel.getEndereco() != null && restauranteModel.getEndereco().getCidade() != null) {
				restauranteModel.getEndereco().getCidade()
						.add(algaLinks.linkToCidade(restaurante.getEndereco().getCidade().getId()));
			}
		}

		return restauranteModel;
	}

	@Override	
	public CollectionModel<RestauranteDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
		return super.toCollectionModel(entities).add(algaLinks.linkToRestaurantes());
	}
}
