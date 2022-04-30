package io.github.jassonluiz.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.jassonluiz.domain.entity.Produto;

public interface Produtos extends JpaRepository<Produto, Integer>{

}
