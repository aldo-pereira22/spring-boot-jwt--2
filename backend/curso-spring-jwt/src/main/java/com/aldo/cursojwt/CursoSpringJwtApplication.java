package com.aldo.cursojwt;

import java.text.SimpleDateFormat;
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
import com.aldo.cursojwt.domain.ItemPedido;
import com.aldo.cursojwt.domain.Pagamento;
import com.aldo.cursojwt.domain.PagamentoComBoleto;
import com.aldo.cursojwt.domain.PagamentoComCartao;
import com.aldo.cursojwt.domain.Pedido;
import com.aldo.cursojwt.domain.Produto;
import com.aldo.cursojwt.domain.enuns.EstadoPagamento;
import com.aldo.cursojwt.domain.enuns.TipoCliente;
import com.aldo.cursojwt.repositories.CategoriaRepository;
import com.aldo.cursojwt.repositories.CidadeRepository;
import com.aldo.cursojwt.repositories.ClienteRepository;
import com.aldo.cursojwt.repositories.EnderecoRepository;
import com.aldo.cursojwt.repositories.EstadoRepository;
import com.aldo.cursojwt.repositories.ItemPedidoRepository;
import com.aldo.cursojwt.repositories.PagamentoRepository;
import com.aldo.cursojwt.repositories.PedidoRepository;
import com.aldo.cursojwt.repositories.ProdutoRepository;
import com.aldo.cursojwt.services.S3Service;

@SpringBootApplication
public class CursoSpringJwtApplication implements CommandLineRunner {

	
	
	@Autowired
	private S3Service s3Service;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursoSpringJwtApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		s3Service.uploadFile("/home/byaldo/Imagens/Captura de tela de 2021-04-30 17-47-36.png");
				
				
		
		
	}

	
}
