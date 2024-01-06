package com.wilsonpedro.parking.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wilsonpedro.parking.models.Address;
import com.wilsonpedro.parking.services.AddressService;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
class AddressExceptionTest {
	
	@Autowired
	AddressService addressService;

	@Test
	void EntityNotFoundExceptionWhenTryingToFetchAddress() {
		
		assertThrows(EntityNotFoundException.class, () -> addressService.findById(70L));
	}
	
	@Test
	void EntityNotFoundExceptionWhenTryingToUpdateAddress() {
		
		Address address = new Address(70L, "52220-251", "Rua das Ameixas", "Flores", "Minas-Gerais");
		
		assertThrows(EntityNotFoundException.class, () -> addressService.update(address, 70L));
	}

	@Test
	void EntityNotFoundExceptionWhenTryingToDeleteAddress() {
		
		assertThrows(EntityNotFoundException.class, () -> addressService.delete(70L));
		
	}
}
