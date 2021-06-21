package com.aldo.cursojwt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.aldo.cursojwt.domain.Categoria;
import com.aldo.cursojwt.repositories.CategoriaRepository;
import com.aldo.cursojwt.services.exceptions.DataIntegrityException;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) throws ObjectNotFoundException {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	
	public  Categoria insert(Categoria categoria) {
		categoria.setId(null);
		return repo.save(categoria);
	}
	
	public  Categoria update(Categoria categoria) throws ObjectNotFoundException {
		find(categoria.getId());
		return repo.save(categoria);
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	public void delete(Integer id) throws ObjectNotFoundException {
		find(id);
		
		try {
			repo.deleteById(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos ");
		}
	}
}







