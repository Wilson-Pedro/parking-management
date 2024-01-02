package com.wilsonpedro.parking.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wilsonpedro.parking.models.Address;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.repositories.AddressRepository;
import com.wilsonpedro.parking.repositories.CompanyRepository;
import com.wilsonpedro.parking.services.CompanyService;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompanyControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	CompanyService companyService;

	@Test
	@Order(1)
	void mustSaveTheCompanySuccessfully() throws Exception{
		
		Address address = new Address(null, "54320-151", "Rua das Ameixas", "Flores", "Minas-Gerais");
		
		addressRepository.save(address);
		
		Company company = new Company(null, "WS-Tecnology", "14326422000166", address, 
				"(95)2256-9123", 30, 20);
		
		String jsonRequest = objectMapper.writeValueAsString(company);
		
		assertEquals(0, companyRepository.count());
		
		mockMvc.perform(post("/companies/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", equalTo(1)))
				.andExpect(jsonPath("$.name", equalTo("WS-Tecnology")))
				.andExpect(jsonPath("$.cnpj", equalTo("14326422000166")))
				.andExpect(jsonPath("$.phone", equalTo("(95)2256-9123")))
				.andExpect(jsonPath("$.spacesForMotorbikes", equalTo(30)))
				.andExpect(jsonPath("$.spacesForCars", equalTo(20)));
		
		assertEquals(1, companyRepository.count());
	}
	
	@Test
	@Order(2)
	void mustFetchAListOfCompaniesSuccessfully() throws Exception{
		
		mockMvc.perform(get("/companies"))
				.andExpect(status().isOk());
	}
	
	@Test
	@Order(3)
	void mustFindForTheCompanyFromTheIdSuccessfully() throws Exception {
		
		mockMvc.perform(get("/companies/{id}", 1L))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", equalTo(1)))
				.andExpect(jsonPath("$.name", equalTo("WS-Tecnology")))
				.andExpect(jsonPath("$.cnpj", equalTo("14326422000166")))
				.andExpect(jsonPath("$.phone", equalTo("(95)2256-9123")))
				.andExpect(jsonPath("$.spacesForMotorbikes", equalTo(30)))
				.andExpect(jsonPath("$.spacesForCars", equalTo(20)));
				
	}
	
	@Test
	@Order(4)
	void mustUpdateTheCompanySuccessfully() throws Exception {
		
		Address address = addressRepository.findById(1L).get();
		Company company = companyService.findById(1L);
		
		assertNotEquals("(95)2256-3413", company.getPhone());
		
		Company companyUpdated = new Company(1L, "WS-Tecnology", "14326422000166", address, 
				"(95)2256-3413", 30, 20);
		
		String jsonRequest = objectMapper.writeValueAsString(companyUpdated);
		
		mockMvc.perform(put("/companies/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.phone", equalTo("(95)2256-3413")));		
	}
	
	@Test
	@Order(5)
	void mustDeleteTheCompanySuccessfully() throws Exception {
		
		assertEquals(1, companyRepository.count());
		
		mockMvc.perform(delete("/companies/{id}", 1L))
				.andExpect(status().isNoContent());
		
		assertEquals(0, companyRepository.count());
	}
}
