package com.wilsonpedro.parking.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wilsonpedro.parking.dtos.AddressDTO;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.repositories.CompanyRepository;
import com.wilsonpedro.parking.services.AddressService;

@SpringBootTest
class AddressExceptionTest {
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Test
	void ExistingCepExceptionWhenTryingToSaveAddress() {
		
		companyRepository.deleteAll();
		
		companyRepository.save(new Company
				(1L, "WP-Movies", "14728343000181", null, "(91) 30794933", 30, 20));
		
		Long companyId = companyRepository.findAll().get(0).getId();
		
		addressService.save(new AddressDTO("77062-082", "Rua das Ameixas", "Flores", "Minas-Gerais", companyId));
	
		AddressDTO addressDTO = new AddressDTO("77062-082", "Rua das Melâncias", "Flores", "Minas-Gerais", 1L);
		
		assertThrows(ExistingCepException.class, () -> addressService.save(addressDTO));
	}

	@Test
	void EntityNotFoundExceptionWhenTryingToFetchAddress() {
		
		assertThrows(NotFoundException.class, () -> addressService.findById(70L));
	}
	
	@Test
	void EntityNotFoundExceptionWhenTryingToUpdateAddress() {
		
		AddressDTO addressDTO = new AddressDTO("52220-251", "Rua das Ameixas", "Flores", "Minas-Gerais", 1L);
		
		assertThrows(NotFoundException.class, () -> addressService.update(addressDTO, 70L));
	}

	@Test
	void EntityNotFoundExceptionWhenTryingToDeleteAddress() {
		
		assertThrows(NotFoundException.class, () -> addressService.delete(70L));
		
	}
}
