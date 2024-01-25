package com.wilsonpedro.parking.exceptionhandler;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.wilsonpedro.parking.exceptions.ExistingCepException;
import com.wilsonpedro.parking.exceptions.ExistingCnpjException;
import com.wilsonpedro.parking.exceptions.ExistingCompanyNameException;
import com.wilsonpedro.parking.exceptions.ExistingPhoneException;
import com.wilsonpedro.parking.exceptions.ExistingPlateException;
import com.wilsonpedro.parking.exceptions.LimitOfSpacesException;
import com.wilsonpedro.parking.exceptions.NotFoundException;
import com.wilsonpedro.parking.exceptions.NotParkedException;
import com.wilsonpedro.parking.exceptions.ParkedException;

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
	
	@ExceptionHandler(ExistingPhoneException.class)
	public ResponseEntity<Problam> existingPhone() {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problam problam = new Problam();
		problam.setTitle("Existing phone!");
		problam.setCode(status.value());
		problam.setDateTime(OffsetDateTime.now());
		
		return ResponseEntity.status(status).body(problam);
	}
	
	@ExceptionHandler(ExistingCompanyNameException.class)
	public ResponseEntity<Problam> existingCompanyName() {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problam problam = new Problam();
		problam.setTitle("Existing Company name!");
		problam.setCode(status.value());
		problam.setDateTime(OffsetDateTime.now());
		
		return ResponseEntity.status(status).body(problam);
	}
	
	@ExceptionHandler(ExistingCepException.class)
	public ResponseEntity<Problam> existingCep() {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problam problam = new Problam();
		problam.setTitle("Existing CEP!");
		problam.setCode(status.value());
		problam.setDateTime(OffsetDateTime.now());
		
		return ResponseEntity.status(status).body(problam);
	}
	
	@ExceptionHandler(ParkedException.class)
	public ResponseEntity<Problam> parked() {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problam problam = new Problam();
		problam.setTitle("The vehicle is already parked!");
		problam.setCode(status.value());
		problam.setDateTime(OffsetDateTime.now());
		
		return ResponseEntity.status(status).body(problam);
	}
	
	@ExceptionHandler(NotParkedException.class)
	public ResponseEntity<Problam> notParked() {
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Problam problam = new Problam();
		problam.setTitle("The vehicle is already not parked!");
		problam.setCode(status.value());
		problam.setDateTime(OffsetDateTime.now());
		
		return ResponseEntity.status(status).body(problam);
	}
}
