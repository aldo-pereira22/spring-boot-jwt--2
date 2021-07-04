package com.aldo.cursojwt.dto;

import java.io.Serializable;

import com.aldo.cursojwt.domain.Estado;

public class EstadoDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	private String nome;
	
	public EstadoDTO() {
		// TODO Auto-generated constructor stub
	}

	public EstadoDTO(Estado obj) {
		this.id = obj.getId();
		this.nome = obj.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		nome = nome;
	}
	
	


}
