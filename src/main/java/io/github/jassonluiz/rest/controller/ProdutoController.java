package io.github.jassonluiz.rest.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.jassonluiz.domain.entity.Produto;
import io.github.jassonluiz.domain.repository.ProdutosRepository;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

	public ProdutosRepository repository;
	
	public ProdutoController( ProdutosRepository repository) {
		this.repository = repository;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Produto salvar( @RequestBody @Valid Produto produto ) {
		return repository.save(produto);
	}
	
	@GetMapping("{id}")
	public Produto getProduto( @PathVariable Integer id ) {
		return repository.findById(id)
							.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encotrado!"));
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar( @PathVariable Integer id ) {
		repository.findById(id)
						.map(produto -> {
							repository.delete(produto);
							return Void.TYPE;
						})
						.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!"));
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar( @PathVariable Integer id, @RequestBody @Valid Produto produtoAtualizado) {
		repository.findById(id)
					.map(produto -> {
						produto.setDescricao(produtoAtualizado.getDescricao());
						produto.setPreco(produtoAtualizado.getPreco());
						
						return repository.save(produto);
					})
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado!"));
	}
	
	@GetMapping
	public List<Produto> find( Produto filtro){
		ExampleMatcher matcher = ExampleMatcher
										.matching()
										.withIgnoreCase()
										.withStringMatcher(
												ExampleMatcher.StringMatcher.CONTAINING );
		
		Example example = Example.of(filtro, matcher);
		return repository.findAll(example);
	}
	
	
}
