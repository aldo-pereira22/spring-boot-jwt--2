package com.aldo.cursojwt.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.aldo.cursojwt.domain.Cliente;
import com.aldo.cursojwt.domain.ItemPedido;
import com.aldo.cursojwt.domain.PagamentoComBoleto;
import com.aldo.cursojwt.domain.Pedido;
import com.aldo.cursojwt.domain.enuns.EstadoPagamento;
import com.aldo.cursojwt.repositories.ItemPedidoRepository;
import com.aldo.cursojwt.repositories.PagamentoRepository;
import com.aldo.cursojwt.repositories.PedidoRepository;
import com.aldo.cursojwt.security.UserSS;
import com.aldo.cursojwt.services.exceptions.AuthorizationException;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	

	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private EmailService emailService;
	

	
	public Pedido find(Integer id) throws ObjectNotFoundException {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	
	@Transactional
	
	public Pedido insert(Pedido obj) throws ObjectNotFoundException {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			try {
				ip.setProduto(produtoService.find(ip.getProduto().getId()));
				ip.setPreco(ip.getProduto().getPreco());
				ip.setPedido(obj);
			} catch (ObjectNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}
	
	
	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) throws ObjectNotFoundException{
		UserSS user = UserService.authenticated();
		if(user == null ) {
			throw new AuthorizationException("Acesso negado!");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}
}













