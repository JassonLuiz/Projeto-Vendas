package io.github.jassonluiz.rest.controller;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import io.github.jassonluiz.domain.entity.Cliente;
import io.github.jassonluiz.domain.repository.ClientesRepository;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

	public ClientesRepository repository;
	
	public ClienteController( ClientesRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping("{id}")
	public Cliente getCliente( @PathVariable Integer id ){
		return repository.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente salvar( @RequestBody Cliente cliente ) {
		return repository.save(cliente);
	}
	
	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar( @PathVariable Integer id) {
		repository.findById(id)
					.map(cliente -> {
						repository.delete(cliente);
						return Void.TYPE;
					})
					.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
	}
	
	@PutMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar( @PathVariable Integer id, @RequestBody Cliente clienteAtualizado) {
		repository.findById(id)
					.map(cliente -> {
						cliente.setNome(clienteAtualizado.getNome());
						return repository.save(cliente);
					})
					.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado!"));
	}
	
	@GetMapping
	public List<Cliente> find( Cliente filtro ) {
		ExampleMatcher matcher = ExampleMatcher
									.matching()
									.withIgnoreCase()
									.withStringMatcher(
											ExampleMatcher.StringMatcher.CONTAINING );
		
		Example example = Example.of(filtro, matcher);
		return repository.findAll(example);	
	}
	
}
