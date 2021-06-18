package com.aldo.cursojwt;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aldo.cursojwt.domain.Categoria;
import com.aldo.cursojwt.domain.Cidade;
import com.aldo.cursojwt.domain.Cliente;
import com.aldo.cursojwt.domain.Endereco;
import com.aldo.cursojwt.domain.Estado;
import com.aldo.cursojwt.domain.Produto;
import com.aldo.cursojwt.domain.enuns.TipoCliente;
import com.aldo.cursojwt.repositories.CategoriaRepository;
import com.aldo.cursojwt.repositories.CidadeRepository;
import com.aldo.cursojwt.repositories.ClienteRepository;
import com.aldo.cursojwt.repositories.EnderecoRepository;
import com.aldo.cursojwt.repositories.EstadoRepository;
import com.aldo.cursojwt.repositories.ProdutoRepository;

@SpringBootApplication
public class CursoSpringJwtApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	EstadoRepository estadoRepository;
	
	@Autowired
	CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursoSpringJwtApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "INFORMÀTICA");
		Categoria cat2 = new Categoria(null, "ESCRITÓRIO");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São paulo", est2);
		Cidade c3 = new Cidade(null,"Campinas", est2);
		
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null,"Maria Silva", "maria@gmail.com","3699999", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("2222", "11111"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303","jardim", "38283", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800","Centro", "8323", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		
	}

	
}
