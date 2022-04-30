package io.github.jassonluiz.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.jassonluiz.domain.entity.ItemPedido;

public interface ItensPedidos extends JpaRepository<ItemPedido, Integer>{

}
