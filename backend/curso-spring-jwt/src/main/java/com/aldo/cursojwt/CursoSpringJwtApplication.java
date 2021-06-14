package com.aldo.cursojwt;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aldo.cursojwt.domain.Categoria;
import com.aldo.cursojwt.repositories.CategoriaRepository;

@SpringBootApplication
public class CursoSpringJwtApplication implements CommandLineRunner {

	@Autowired
	CategoriaRepository categoriaRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursoSpringJwtApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "INFORMÀTICA");
		Categoria cat2 = new Categoria(null, "ESCRITÓRIO");
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		
	}

	
}
