package com.aldo.cursojwt.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String msg) {

		super(msg);
	}
	
	public ObjectNotFoundException( String msg, Throwable cause ) {
		super(msg, cause);
	}
	
	public ObjectNotFoundException() {
		// TODO Auto-generated constructor stub
	}
}
