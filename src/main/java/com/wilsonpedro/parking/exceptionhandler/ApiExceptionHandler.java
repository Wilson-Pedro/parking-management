package com.wilsonpedro.parking.exceptionhandler;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
}
