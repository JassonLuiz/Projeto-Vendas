package io.github.jassonluiz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.github.jassonluiz.domain.entity.Cliente;
import io.github.jassonluiz.domain.repository.Clientes;


@SpringBootApplication
public class VendasApplication {
	
	@Bean
	public CommandLineRunner init(@Autowired Clientes clientes) {
		return args -> {
			System.out.println("Salvando clientes");
			clientes.save(new Cliente("Fulano"));
			clientes.save(new Cliente("Anne"));
			
			List<Cliente> result =  clientes.encontrarPorNome("Jasson");
			result.forEach(System.out::println);
			
			
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
