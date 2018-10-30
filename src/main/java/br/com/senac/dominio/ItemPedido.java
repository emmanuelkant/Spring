package br.com.senac.dominio;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class ItemPedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ItemPedidoPK id = new ItemPedidoPK();

	private Double desconto;
	private Integer quantidade;
	private Double valor;

	public ItemPedido() {
		super();
	}

	public ItemPedido(Pedido pedido, Curso curso, Double desconto, Integer quantidade, Double valor) {
		super();
		id.setPedido(pedido);
		id.setCurso(curso);
		this.desconto = desconto;
		this.quantidade = quantidade;
		this.valor = valor;
	}

	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Pedido getPedido() {
		return this.getPedido();
	}

	public Curso getCurso() {
		return this.id.getCurso();
	}

}