package com.wilsonpedro.parking.exceptions;

public class ExistingCnpjException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ExistingCnpjException(String cnpj) {
		super("Existing CNPJ: " + cnpj);
	}
}
