package io.github.jassonluiz.domain.service;

import io.github.jassonluiz.domain.entity.Pedido;
import io.github.jassonluiz.rest.dto.PedidoDTO;

public interface PedidoService {
	Pedido salvar(PedidoDTO dto);
}
