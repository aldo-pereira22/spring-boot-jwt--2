package com.aldo.cursojwt.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.aldo.cursojwt.domain.Cliente;
import com.aldo.cursojwt.services.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	@NotEmpty(message = "Preenchimento obrigatório!")
	@Length(min = 5, max = 60, message = "O tamanho deve ser entre 5 e 60 caracteres")
	private String nome;
	
	@NotEmpty(message = "Preenchimento obrigatório!")
	@Email(message = "Email inválido")
	private String email;
	
	public ClienteDTO() {
		// TODO Auto-generated constructor stub
	}

	public ClienteDTO(Cliente obj) {
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
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
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
