package io.github.jassonluiz.rest.controller;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.util.CollectionUtils;

import io.github.jassonluiz.domain.entity.ItemPedido;
import io.github.jassonluiz.domain.entity.Pedido;
import io.github.jassonluiz.domain.enums.StatusPedido;
import io.github.jassonluiz.domain.service.PedidoService;
import io.github.jassonluiz.rest.dto.AtualizacaoStatusPedidoDTO;
import io.github.jassonluiz.rest.dto.InformacaoItemPedidoDTO;
import io.github.jassonluiz.rest.dto.InformacoesPedidoDTO;
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
	public Integer save( @RequestBody @Valid PedidoDTO dto ) {
		Pedido pedido = service.salvar(dto);
		return pedido.getId();
	}
	
	@GetMapping("{id}")
	public InformacoesPedidoDTO getById(@PathVariable Integer id) {
		return service
					.obterPedidoCompleto(id)
					.map(p -> converter(p))
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado!"));
	}
	
	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateStatus( @PathVariable Integer id, @RequestBody AtualizacaoStatusPedidoDTO dto) {
		String novoStatus = dto.getNovoStatus();
		service.atualizaStatus(id, StatusPedido.valueOf(novoStatus));
	}
	
	public InformacoesPedidoDTO converter(Pedido pedido) {
		return InformacoesPedidoDTO
				.builder()
				.codigo(pedido.getId())
				.dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
				.cpf(pedido.getCliente().getCpf())
				.nomeCliente(pedido.getCliente().getNome())
				.total(pedido.getTotal())
				.status(pedido.getStatus().name())
				.itens(converter(pedido.getItens()))
				.build();
	}
	
	private List<InformacaoItemPedidoDTO> converter(List<ItemPedido> itens){
		if(CollectionUtils.isEmpty(itens)) {
			return Collections.emptyList();
		}
		
		return itens.stream().map(
					item -> InformacaoItemPedidoDTO
								.builder()
								.descricaoProduto(item.getProduto().getDescricao())
								.precoUnitario(item.getProduto().getPreco())
								.quantidade(item.getQuantidade())
								.build()
				).collect(Collectors.toList());
	}
	
	
}
