package br.com.senac.dominio;

import java.io.Serializable;

public class Item implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Curso curso;
	
	private int quantidade;

	public Item(Curso curso, int quantidade) {
		super();
		this.curso = curso;
		this.quantidade = quantidade;
	}
	
	public Item() {
		super();
	}
	
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	
}
