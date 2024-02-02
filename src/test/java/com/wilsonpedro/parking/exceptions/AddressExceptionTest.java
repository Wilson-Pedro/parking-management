package com.wilsonpedro.parking.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wilsonpedro.parking.dtos.AddressDTO;
import com.wilsonpedro.parking.models.Address;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.repositories.CompanyRepository;
import com.wilsonpedro.parking.services.AddressService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressExceptionTest {
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Test
	@Order(1)
	void ExistingCepExceptionWhenTryingToSaveAddress() {
		
		companyRepository.deleteAll();
		
		companyRepository.save(new Company
				(1L, "WP-Movies", "14728343000181", null, "(91) 30794933", 30, 20));
		
		Long companyId = companyRepository.findAll().get(0).getId();
		
		addressService.save(new Address(1L, "77062-082", "Rua das Melâncias", "Flores", "Minas-Gerais"), companyId);
	
		Address address = new Address(2L, "77062-082", "Rua das Melâncias", "Flores", "Minas-Gerais");
		
		assertThrows(ExistingCepException.class, () -> addressService.save(address, 1L));
	}
	
	@Test
	@Order(2)
	void ExistingCepExceptionWhenTryingToUpdateAddress() {
		
		companyRepository.save(new Company
				(2L, "AS-Movies", "2778343000191", null, "(98)10012-9493", 30, 20));
		
		Long companyId = companyRepository.findAll().get(1).getId();
		
		addressService.save(new Address(3L, "22062-081", "Rua das Ameixas", "Flores", "Minas-Gerais"), companyId);
		
		Long addressId = addressService.findAll().get(0).getId();
		var address = addressService.findById(addressId);
		address.setCep("22062-081");
		
		assertThrows(ExistingCepException.class, () -> addressService.update(address, addressId));
	}

	@Test
	void EntityNotFoundExceptionWhenTryingToFetchAddress() {
		
		assertThrows(NotFoundException.class, () -> addressService.findById(70L));
	}
	
	@Test
	void EntityNotFoundExceptionWhenTryingToUpdateAddress() {
		
		Address address = new Address(4L, "52220-251", "Rua das Ameixas", "Flores", "Minas-Gerais");
		
		assertThrows(NotFoundException.class, () -> addressService.update(address, 70L));
	}

	@Test
	void EntityNotFoundExceptionWhenTryingToDeleteAddress() {
		
		assertThrows(NotFoundException.class, () -> addressService.delete(70L));
		
	}
}
