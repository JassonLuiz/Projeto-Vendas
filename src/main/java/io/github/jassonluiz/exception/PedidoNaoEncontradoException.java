package io.github.jassonluiz.exception;

public class PedidoNaoEncontradoException extends RuntimeException{
	public PedidoNaoEncontradoException() {
		super("Pedido não encontrado.");
	}
}
