package com.aldo.cursojwt.services.exceptions;

public class DataIntegrityException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public DataIntegrityException(String msg) {

		super(msg);
	}
	
	public DataIntegrityException( String msg, Throwable cause ) {
		super(msg, cause);
	}
	
	public DataIntegrityException() {
		// TODO Auto-generated constructor stub
	}
}
