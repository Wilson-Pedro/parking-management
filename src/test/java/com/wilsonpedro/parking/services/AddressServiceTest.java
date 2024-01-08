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

import com.wilsonpedro.parking.dtos.AddressDTO;
import com.wilsonpedro.parking.dtos.CompanyInputDTO;
import com.wilsonpedro.parking.models.Address;
import com.wilsonpedro.parking.repositories.AddressRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressServiceTest {
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	CompanyService companyService;
	
	@Test
	@Order(1)
	void mustSaveTheAddressSuccessfully() {
		
		addressRepository.deleteAll();
		
		CompanyInputDTO companyInputDto = new CompanyInputDTO("WS-Tecnology", "14326422000166", 
				"(95)2256-9123", 30, 20);
		
		companyService.save(companyInputDto);
		
		Long companyId = companyService.findAll().get(0).getId();
		
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setId(null);
		addressDTO.setCep("80120-111");
		addressDTO.setStreet("Rua das Ameixas");
		addressDTO.setNeighborhood("Flores");
		addressDTO.setCity("Minas-Gerais");
		addressDTO.setCompanyId(companyId);
		
		assertEquals(0, this.addressRepository.count());
		
		Address addressSaved = this.addressService.save(addressDTO);
		
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
		
		AddressDTO addressDTO = new AddressDTO(address);
		
		Address addressUpdated = addressService.update(addressDTO, id);
		
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
