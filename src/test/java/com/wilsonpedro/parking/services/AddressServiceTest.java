package com.wilsonpedro.parking.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wilsonpedro.parking.models.Address;
import com.wilsonpedro.parking.repositories.AddressRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressServiceTest {
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Test
	@Order(1)
	void mustSaveTheAddressSuccessfully() {
		
		addressRepository.deleteAll();
		
		Address address = new Address();
		address.setId(null);
		address.setCep("80120-111");
		address.setStreet("Rua das Ameixas");
		address.setNeighborhood("Flores");
		address.setCity("Minas-Gerais");
		
		assertEquals(0, this.addressRepository.count());
		
		addressRepository.save(address);
		Address addressSaved = this.addressService.save(address);
		
		assertNotNull(addressSaved.getId());
		assertEquals(1, this.addressRepository.count());
	}

	@Test
	@Order(2) 
	void mustFetchAListOfAddresesSuccessFully() {
		
		List<Address> list = addressService.findAll();
		
		assertNotNull(list);
		assertEquals(list.size(), addressRepository.count());
	}
	
	@Test
	@Order(3)
	void mustFindForTheAddressFromTheIdSuccessfully() {
		
		Long id = addressService.findAll().get(0).getId();
		
		Address address = addressService.findById(id);
		
		assertNotNull(address);
		assertEquals(id, address.getId());
		assertEquals("80120-111", address.getCep());
		assertEquals("Rua das Ameixas", address.getStreet());
		assertEquals("Flores", address.getNeighborhood());
		assertEquals("Minas-Gerais", address.getCity());
	}
	
	@Test
	@Order(4)
	void mustUpdateTheAddressSuccessfully() {
		
		Long id = addressService.findAll().get(0).getId();
		
		Address address = addressService.findById(id);
		
		assertNotEquals("Rua das Maçãs", address.getStreet());
		address.setStreet("Rua das Maçãs");
		
		Address addressUpdated = addressService.update(address, id);
		
		assertEquals("Rua das Maçãs", addressUpdated.getStreet());
	}
	
	@Test
	@Order(5)
	void mustDeleteTheAddressSuccessfully() {
		
		Long id = addressService.findAll().get(0).getId();
		
		assertEquals(1, addressRepository.count());
		
		addressService.delete(id);
		
		assertEquals(0, addressRepository.count());
	}
}
