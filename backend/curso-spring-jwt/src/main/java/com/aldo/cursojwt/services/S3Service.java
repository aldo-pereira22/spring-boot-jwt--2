package com.aldo.cursojwt.services;

import java.io.File;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;


@Service
public class S3Service {

	private Logger LOG = org.slf4j.LoggerFactory.getLogger(S3Service.class);
	@Autowired
	private AmazonS3 s3Client;

	@Value("${s3.bucket}")
	private String bucktName;

	public void uploadFile(String localFilePath) {

		try {
			File file = new File(localFilePath);
			LOG.info("Iniciando UPLOAD");
			
			s3Client.putObject(new PutObjectRequest(bucktName, "teste.png", file));
			
			LOG.info("UPLOAD finalizado! ");
		} catch (AmazonServiceException e) {
			LOG.info("AmazonServiceException: "+e.getErrorMessage());
			LOG.info("Status code: "+e.getErrorCode());
			
		}catch (AmazonClientException e) {
			LOG.info("AmazonClientException "+ e.getMessage());
			
		}

	}
}







