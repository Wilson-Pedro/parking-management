package com.wilsonpedro.parking.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wilsonpedro.parking.dtos.CompanyInputDTO;
import com.wilsonpedro.parking.services.CompanyService;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
class CompanyServiceExceptionTest {
	
	@Autowired
	CompanyService companyService;

	@Test
	void EntityNotFoundExceptionWhenTryingToFetchCompany() {
		
		assertThrows(EntityNotFoundException.class, () -> companyService.findById(70L));
	}
	
	@Test
	void EntityNotFoundExceptionWhenTryingToUpdateCompany() {
		
		CompanyInputDTO company = new CompanyInputDTO("WS-Tecnology", "14326422000166", 
				"(95)2256-9123", 30, 20);
		
		assertThrows(EntityNotFoundException.class, () -> companyService.update(company, 70L));
	}

	@Test
	void EntityNotFoundExceptionWhenTryingToDeleteCompany() {
		
		assertThrows(EntityNotFoundException.class, () -> companyService.delete(70L));
		
	}
}
