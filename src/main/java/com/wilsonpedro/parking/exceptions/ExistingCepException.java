package com.wilsonpedro.parking.exceptions;

public class ExistingCepException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ExistingCepException(String cep) {
		super("Existing CNPJ: " + cep);
	}
}
