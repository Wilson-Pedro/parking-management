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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wilsonpedro.parking.dtos.AddressDTO;
import com.wilsonpedro.parking.dtos.CompanyInputDTO;
import com.wilsonpedro.parking.dtos.RegistroDTO;
import com.wilsonpedro.parking.dtos.records.AuthenticationDTO;
import com.wilsonpedro.parking.enums.UserRole;
import com.wilsonpedro.parking.infra.security.TokenService;
import com.wilsonpedro.parking.models.Address;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.models.User;
import com.wilsonpedro.parking.repositories.AddressRepository;
import com.wilsonpedro.parking.repositories.UserRepository;
import com.wilsonpedro.parking.services.AddressService;
import com.wilsonpedro.parking.services.CompanyService;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AddressControllerTest {
	
	private static String TOKEN;
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
		
	@Autowired
	AddressService addressService;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TokenService tokenService;
	
	@Test
	@Order(1)
	void mustRegisterTheUserSuccessfully() {
		userRepository.deleteAll();
		
		RegistroDTO registroDTO = new RegistroDTO("pedro", "12345", UserRole.ADMIN);
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(registroDTO.getPassword());
		
		assertNotNull(encryptedPassword);
		assertNotEquals(encryptedPassword, registroDTO.getPassword());
		
		User user = new User(registroDTO.getLogin(), encryptedPassword, registroDTO.getRole());
		
		assertEquals(0, userRepository.count());
		
		userRepository.save(user);
		
		assertEquals(1, userRepository.count());
		assertEquals(UserRole.ADMIN, user.getRole());
	}

	@Test
	@Order(2)
	void mustRealizeLoginSuccessfully() {
		AuthenticationDTO dto = new AuthenticationDTO("pedro", "12345");
		var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = this.tokenService.generateToken((User) auth.getPrincipal());
		
		assertNotNull(token);
		TOKEN = token;
	}

	@Test
	@Order(3)
	void mustSaveTheAddressSuccessfully() throws Exception{
		
		addressRepository.deleteAll();
		
		CompanyInputDTO companyInputDto = new CompanyInputDTO("WS-Tecnology", "14326422000166", 
				"(95)2256-9123", 30, 20);
		
		companyService.save(new Company(companyInputDto));
		
		Long companyId = companyService.findAll().get(0).getId();
		
		
		AddressDTO addressDTO = new AddressDTO("54320-151", "Rua das Ameixas", "Flores", "Minas-Gerais", companyId);
		
		String jsonRequest = objectMapper.writeValueAsString(addressDTO);
		
		assertEquals(0, addressRepository.count());
		
		mockMvc.perform(post("/addreses/")
				.header("Authorization", "Bearer " + TOKEN)
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
	@Order(4)
	void mustFetchAListOfAddresesSuccessfully() throws Exception{
		
		mockMvc.perform(get("/addreses")
				.header("Authorization", "Bearer " + TOKEN))
				.andExpect(status().isOk());
	}
	
	@Test
	@Order(5)
	void mustFindForTheAddressFromTheIdSuccessfully() throws Exception {
		
		Long id = addressRepository.findAll().get(0).getId();
		
		mockMvc.perform(get("/addreses/{id}", id)
				.header("Authorization", "Bearer " + TOKEN))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", equalTo(id.intValue())))
				.andExpect(jsonPath("$.cep", equalTo("54320-151")))
				.andExpect(jsonPath("$.street", equalTo("Rua das Ameixas")))
				.andExpect(jsonPath("$.neighborhood", equalTo("Flores")))
				.andExpect(jsonPath("$.city", equalTo("Minas-Gerais")));	
	}
	
	@Test
	@Order(6)
	void mustUpdateTheAddressSuccessfully() throws Exception {
		
		Long id = addressRepository.findAll().get(0).getId();
		Address address = addressService.findById(id);
		
		assertNotEquals("Rua das Laranjas", address.getStreet());
		
		Long companyId = companyService.findAll().get(0).getId();
		
		AddressDTO addressUpdated = new AddressDTO("52220-251", "Rua das Laranjas", "Flores", "Minas-Gerais", companyId);
		
		String jsonRequest = objectMapper.writeValueAsString(addressUpdated);
		
		mockMvc.perform(put("/addreses/{id}", id)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.street", equalTo("Rua das Laranjas")));
		
		Address addressFinded = addressService.findById(id);
		
		assertEquals("Rua das Laranjas", addressFinded.getStreet());
	}
	
	@Test
	@Order(7)
	void mustDeleteTheCompanySuccessfully() throws Exception {
		
		Long id = addressRepository.findAll().get(0).getId();
		
		assertEquals(1, addressRepository.count());
		
		mockMvc.perform(delete("/addreses/{id}", id)
				.header("Authorization", "Bearer " + TOKEN))
				.andExpect(status().isNoContent());
		
		assertEquals(0, addressRepository.count());
	}
}
