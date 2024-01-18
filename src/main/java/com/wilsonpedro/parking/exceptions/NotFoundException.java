package com.wilsonpedro.parking.exceptions;

public class NotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public NotFoundException(final Long id) {
		super(String.format("Entity with Id: [%s] not found", id));
	}
}
