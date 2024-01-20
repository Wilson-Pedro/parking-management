package com.wilsonpedro.parking.exceptionhandler;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.wilsonpedro.parking.exceptions.ExistingCnpjException;
import com.wilsonpedro.parking.exceptions.ExistingPlateException;
import com.wilsonpedro.parking.exceptions.LimitOfSpacesException;
import com.wilsonpedro.parking.exceptions.NotFoundException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Problam> notFound() {
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		Problam problam = new Problam();
		problam.setTitle("Entity not found");
		problam.setCode(status.value());
		problam.setDateTime(OffsetDateTime.now());
		
		return ResponseEntity.status(status).body(problam);
	}
	
	@ExceptionHandler(ExistingPlateException.class)
	public ResponseEntity<Problam> existingPlate() {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problam problam = new Problam();
		problam.setTitle("Existing Plate");
		problam.setCode(status.value());
		problam.setDateTime(OffsetDateTime.now());
		
		return ResponseEntity.status(status).body(problam);
	}
	
	@ExceptionHandler(ExistingCnpjException.class)
	public ResponseEntity<Problam> existingCnpj() {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problam problam = new Problam();
		problam.setTitle("Existing CNPJ");
		problam.setCode(status.value());
		problam.setDateTime(OffsetDateTime.now());
		
		return ResponseEntity.status(status).body(problam);
	}
	
	@ExceptionHandler(LimitOfSpacesException.class)
	public ResponseEntity<Problam> limitOfSpaces() {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problam problam = new Problam();
		problam.setTitle("There is no more spaces to park in this Company!");
		problam.setCode(status.value());
		problam.setDateTime(OffsetDateTime.now());
		
		return ResponseEntity.status(status).body(problam);
	}
}
