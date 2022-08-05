package io.github.jassonluiz.domain.service;

import java.util.Optional;

import io.github.jassonluiz.domain.entity.Pedido;
import io.github.jassonluiz.rest.dto.PedidoDTO;

public interface PedidoService {
	Pedido salvar(PedidoDTO dto);
	
	Optional<Pedido> obterPedidoCompleto(Integer id);
}
