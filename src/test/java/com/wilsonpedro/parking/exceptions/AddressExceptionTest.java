package com.wilsonpedro.parking.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wilsonpedro.parking.dtos.AddressDTO;
import com.wilsonpedro.parking.services.AddressService;

@SpringBootTest
class AddressExceptionTest {
	
	@Autowired
	AddressService addressService;

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
