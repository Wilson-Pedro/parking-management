package com.wilsonpedro.parking.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.wilsonpedro.parking.dtos.AddressDTO;
import com.wilsonpedro.parking.models.Address;
import com.wilsonpedro.parking.repositories.AddressRepository;
import com.wilsonpedro.parking.services.AddressService;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	AddressService addressService;
	
	@Autowired
	AddressRepository addressRepository;

	@Test
	@Order(1)
	void mustSaveTheAddressSuccessfully() throws Exception{
		
		addressRepository.deleteAll();
		
		AddressDTO addressDTO = new AddressDTO("54320-151", "Rua das Ameixas", "Flores", "Minas-Gerais");
		
		String jsonRequest = objectMapper.writeValueAsString(addressDTO);
		
		assertEquals(0, addressRepository.count());
		
		mockMvc.perform(post("/addreses/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.cep", equalTo("54320-151")))
				.andExpect(jsonPath("$.street", equalTo("Rua das Ameixas")))
				.andExpect(jsonPath("$.neighborhood", equalTo("Flores")))
				.andExpect(jsonPath("$.city", equalTo("Minas-Gerais")));
		
		Long id = addressRepository.findAll().get(0).getId();
		
		assertNotNull(id);
		assertEquals(1, addressRepository.count());
	}
	
	@Test
	@Order(2)
	void mustFetchAListOfAddresesSuccessfully() throws Exception{
		
		mockMvc.perform(get("/addreses"))
				.andExpect(status().isOk());
	}
	
	@Test
	@Order(3)
	void mustFindForTheAddressFromTheIdSuccessfully() throws Exception {
		
		Long id = addressRepository.findAll().get(0).getId();
		
		mockMvc.perform(get("/addreses/{id}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", equalTo(id.intValue())))
				.andExpect(jsonPath("$.cep", equalTo("54320-151")))
				.andExpect(jsonPath("$.street", equalTo("Rua das Ameixas")))
				.andExpect(jsonPath("$.neighborhood", equalTo("Flores")))
				.andExpect(jsonPath("$.city", equalTo("Minas-Gerais")));	
	}
	
	@Test
	@Order(4)
	void mustUpdateTheAddressSuccessfully() throws Exception {
		
		Long id = addressRepository.findAll().get(0).getId();
		Address address = addressService.findById(id);
		
		assertNotEquals("Rua das Laranjas", address.getStreet());
		
		AddressDTO addressUpdated = new AddressDTO("52220-251", "Rua das Laranjas", "Flores", "Minas-Gerais");
		
		String jsonRequest = objectMapper.writeValueAsString(addressUpdated);
		
		mockMvc.perform(put("/addreses/{id}", id)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.street", equalTo("Rua das Laranjas")));
		
		Address addressFinded = addressService.findById(id);
		
		assertEquals("Rua das Laranjas", addressFinded.getStreet());
	}
	
	@Test
	@Order(5)
	void mustDeleteTheCompanySuccessfully() throws Exception {
		
		Long id = addressRepository.findAll().get(0).getId();
		
		assertEquals(1, addressRepository.count());
		
		mockMvc.perform(delete("/addreses/{id}", id))
				.andExpect(status().isNoContent());
		
		assertEquals(0, addressRepository.count());
	}
}
