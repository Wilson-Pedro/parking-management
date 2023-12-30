package com.wilsonpedro.parking.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wilsonpedro.parking.models.Address;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.repositories.AddressRepository;
import com.wilsonpedro.parking.repositories.CompanyRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompanyServiceTest {
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	AddressRepository addressRepository;

	@Test
	void mustSaveTheCompanySuccessfully() {
		
		Address address = new Address(null, "54320-151", "Rua das Ameixas", "Flores", "Minas-Gerais");
		
		Company company = new Company(null, "WS-Tecnology", "14326422000166", address, 
				"(95) 3456-7413", 30, 20);
		
		assertEquals(0, companyRepository.count());
		
		addressRepository.save(address);
		Company companySaved = companyService.save(company);
		
		assertNotNull(companySaved.getId());
		assertEquals(1, companyRepository.count());
	}

}
