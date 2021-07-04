package com.aldo.cursojwt.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aldo.cursojwt.domain.Cidade;
import com.aldo.cursojwt.repositories.CidadeRepository;


@Service
public class CidadeService {

	
	@Autowired
	private CidadeRepository repo;
	public List<Cidade> findByEstado(Integer estadoId){
		return repo.findCidades(estadoId);
	}
}
