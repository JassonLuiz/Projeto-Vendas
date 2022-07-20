package io.github.jassonluiz.rest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.jassonluiz.domain.entity.Pedido;
import io.github.jassonluiz.domain.service.PedidoService;
import io.github.jassonluiz.rest.dto.PedidoDTO;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

	private PedidoService service;
	
	public PedidoController(PedidoService serivce) {
		this.service = serivce;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Integer save( @RequestBody PedidoDTO dto ) {
		Pedido pedido = service.salvar(dto);
		return pedido.getId();
	}
}
