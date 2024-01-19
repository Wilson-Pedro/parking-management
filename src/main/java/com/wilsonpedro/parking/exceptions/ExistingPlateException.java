package com.wilsonpedro.parking.exceptions;

public class ExistingPlateException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public ExistingPlateException(String plate) {
		super("Existing plate: " + plate);
	}
}
