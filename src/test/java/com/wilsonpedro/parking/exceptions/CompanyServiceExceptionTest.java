package com.wilsonpedro.parking.exceptions;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wilsonpedro.parking.models.Address;
import com.wilsonpedro.parking.models.Company;
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
		
		Address address = new Address(70L, "54320-151", "Rua das Ameixas", "Flores", "Minas-Gerais");
		
		Company company = new Company(70L, "WS-Tecnology", "14326422000166", address, 
				"(95)2256-9123", 30, 20);
		
		assertThrows(EntityNotFoundException.class, () -> companyService.update(company, 70L));
	}

	@Test
	void EntityNotFoundExceptionWhenTryingToDeleteCompany() {
		
		assertThrows(EntityNotFoundException.class, () -> companyService.delete(70L));
		
	}
}
