package io.github.jassonluiz.domain.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import io.github.jassonluiz.domain.entity.Cliente;
import io.github.jassonluiz.domain.entity.ItemPedido;
import io.github.jassonluiz.domain.entity.Pedido;
import io.github.jassonluiz.domain.entity.Produto;
import io.github.jassonluiz.domain.repository.ClientesRepository;
import io.github.jassonluiz.domain.repository.ItensPedidosRepository;
import io.github.jassonluiz.domain.repository.PedidoRepository;
import io.github.jassonluiz.domain.repository.ProdutosRepository;
import io.github.jassonluiz.domain.service.PedidoService;
import io.github.jassonluiz.exception.RegraNegocioException;
import io.github.jassonluiz.rest.dto.ItemPedidoDTO;
import io.github.jassonluiz.rest.dto.PedidoDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService{

	private final PedidoRepository repository;
	private final ClientesRepository clientesRepository;
	private final ProdutosRepository produtosRepository;
	private final ItensPedidosRepository itensPedidosRepository;

	@Override
	@Transactional
	public Pedido salvar(PedidoDTO dto) {
		Integer idCliente = dto.getCliente();
		Cliente cliente = clientesRepository
								.findById(idCliente)
								.orElseThrow(() -> new RegraNegocioException("Código de cliente inválido!"));
		
		Pedido pedido = new Pedido();
		pedido.setTotal(dto.getTotal());
		pedido.setDataPedido(LocalDate.now());
		pedido.setCliente(cliente);
		
		List<ItemPedido> itensPedido = converterItens(pedido, dto.getItens());
		repository.save(pedido);
		itensPedidosRepository.saveAll(itensPedido);
		pedido.setItens(itensPedido);
		
		return pedido;
	}
	
	private List<ItemPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens) {
		if(itens.isEmpty()) {
			throw new RegraNegocioException("Não é possível realizar um pedido sem itens!");
		}
		
		return itens
				.stream()
				.map( dto -> {
					Integer idProduto = dto.getProduto();
					Produto produto = produtosRepository
							.findById(idProduto)
							.orElseThrow(() -> new RegraNegocioException(
												"Código de produto inválido: " + idProduto
										));
					
					ItemPedido itemPedido = new ItemPedido();
					itemPedido.setQuantidade(dto.getQuantidade());
					itemPedido.setPedido(pedido);
					itemPedido.setProduto(produto);
					return itemPedido;
				}).collect(Collectors.toList());
	}
	
}
