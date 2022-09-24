package io.github.jassonluiz.domain.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(length = 100)
	@NotEmpty(message = "{campo.nome.obrigatorio}")
	private String nome;
	
	@Column(name = "cpf", length = 11)
	@NotEmpty(message = "{campo.cpf.obrigatorio}")
	@CPF(message = "{campo.cpf.invalido}")
	private String cpf;
	
	@JsonIgnore
	@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
	private Set<Pedido> pedidos;
	
	public Cliente(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}
	
}
