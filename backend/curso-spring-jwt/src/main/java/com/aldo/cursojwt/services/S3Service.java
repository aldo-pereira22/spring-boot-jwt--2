package com.aldo.cursojwt.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class S3Service {

	private Logger LOG = org.slf4j.LoggerFactory.getLogger(S3Service.class);
	@Autowired
	private AmazonS3 s3Client;

	@Value("${s3.bucket}")
	private String bucktName;

	public URI uploadFile(MultipartFile multiPartFile) {
		try {

			String fileName = multiPartFile.getOriginalFilename();
			InputStream is = multiPartFile.getInputStream();
			String contentType = multiPartFile.getContentType();
			return uploadFile(is, fileName, contentType);

		} catch (IOException e) {
			throw new RuntimeException("Erro de IO: "+e.getMessage());
		}

	}

	public URI uploadFile(InputStream is, String fileName, String contentType) {
		try {

			ObjectMetadata meta = new ObjectMetadata();

			meta.setContentType(contentType);
			LOG.info("Iniciando UPLOAD");

			s3Client.putObject(bucktName, fileName, is, meta);

			LOG.info("UPLOAD finalizado! ");

			return s3Client.getUrl(bucktName, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new RuntimeException("Erro ao converter URL para URI");
		}
	}
}
