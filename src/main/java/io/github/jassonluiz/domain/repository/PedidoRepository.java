package io.github.jassonluiz.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.jassonluiz.domain.entity.Cliente;
import io.github.jassonluiz.domain.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer>{

	List<Pedido> findByCliente(Cliente cliente);
	
	Optional<Pedido> findByIdFetchItens(Integer id);
}
