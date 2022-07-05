package io.github.jassonluiz.domain.service.impl;

import org.springframework.stereotype.Service;

import io.github.jassonluiz.domain.repository.PedidosRepository;
import io.github.jassonluiz.domain.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService{

	private PedidosRepository repository;
	
	public PedidoServiceImpl(PedidosRepository repository) {
		this.repository = repository;
	}
}
