package com.aldo.cursojwt.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aldo.cursojwt.domain.Cidade;
import com.aldo.cursojwt.domain.Cliente;
import com.aldo.cursojwt.domain.Endereco;
import com.aldo.cursojwt.domain.enuns.Perfil;
import com.aldo.cursojwt.domain.enuns.TipoCliente;
import com.aldo.cursojwt.dto.ClienteDTO;
import com.aldo.cursojwt.dto.ClienteNewDTO;
import com.aldo.cursojwt.repositories.CidadeRepository;
import com.aldo.cursojwt.repositories.ClienteRepository;
import com.aldo.cursojwt.repositories.EnderecoRepository;
import com.aldo.cursojwt.security.UserSS;
import com.aldo.cursojwt.services.exceptions.AuthorizationException;
import com.aldo.cursojwt.services.exceptions.DataIntegrityException;

import javassist.tools.rmi.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private  BCryptPasswordEncoder pe;
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private S3Service  s3service;
	
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ImageService imageService;
	
	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;

	
	
	public Cliente find(Integer id) throws ObjectNotFoundException {
		
		UserSS user = UserService.authenticated();
		if(user ==null || !user.hasHorle(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado! ");
		}
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto n??o encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public  Cliente update(Cliente obj) throws ObjectNotFoundException {
		Cliente newObjt  =  find(obj.getId());
		updateData(newObjt,obj);
		return repo.save(newObjt);
	}
	 
	
	public void delete(Integer id) throws ObjectNotFoundException {
		find(id);
		
		try {
			repo.deleteById(id);
			
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("N??o ?? poss??vel exlcuir porque h?? pedidos relacionados");
		}
	}
	
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	
	public Cliente findByEmail(String email) throws ObjectNotFoundException {
	
		UserSS user = UserService.authenticated();
		
		if(user == null || !user.hasHorle(Perfil.ADMIN) && !email.equals(user.getUsername())) {
			throw new AuthorizationException("Acesso negado! ");
		}
		
		Cliente obj = repo.findByEmail(email);
		if(obj == null ) {
			throw new ObjectNotFoundException("Objeto n??o encontrado! Id: "+user.getId() + " Tipo: " + Cliente.class.getName());
		}
		
		return obj;
				
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(),null, null, null);
	}
	
//	public Cliente fromDTO(ClienteNewDTO objDto) {
//		
//		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()));
//		Cidade cid  = new Cidade(objDto.getCiadeId(), null, null);
//		
//		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
//		cli.getEnderecos().add(end);
//		
//		cli.getTelefones().add(objDto.getTelefone1());
//		
//		if(objDto.getTelefone2() != null) {
//			cli.getTelefones().add(objDto.getTelefone2());
//		}
//		if(objDto.getTelefone3() != null) {
//			cli.getTelefones().add(objDto.getTelefone3());
//		}
//		return cli;
//	}
	
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
				
	
		Cidade cid = new Cidade();		
		cid.setId(objDto.getCidadeId());
//		cidadeRepository.save(cid);

		System.out.println("\n\nCIDADE OBJETO DTO:"+objDto.getCidadeId());
		System.out.println("CIDADE ID: "+cid.getId());
		System.out.println("CIDADE ESTADO: "+cid.getEstado());
		System.out.println("CIDADE NOME : "+cid.getNome() +"\n");

		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
	
	
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	
	public URI uploadProfilePicture(MultipartFile multiPartFIle) {
		
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AuthorizationException("Acesso Negado! ");
		}
		
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multiPartFIle);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
	