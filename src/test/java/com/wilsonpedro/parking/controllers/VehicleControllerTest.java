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
import com.wilsonpedro.parking.dtos.RegistroDTO;
import com.wilsonpedro.parking.dtos.VehicleDTO;
import com.wilsonpedro.parking.dtos.records.AuthenticationDTO;
import com.wilsonpedro.parking.enums.UserRole;
import com.wilsonpedro.parking.enums.VehicleStatus;
import com.wilsonpedro.parking.infra.security.TokenService;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.models.User;
import com.wilsonpedro.parking.models.Vehicle;
import com.wilsonpedro.parking.repositories.AddressRepository;
import com.wilsonpedro.parking.repositories.CompanyRepository;
import com.wilsonpedro.parking.repositories.UserRepository;
import com.wilsonpedro.parking.repositories.VehicleRepository;
import com.wilsonpedro.parking.services.CompanyService;
import com.wilsonpedro.parking.services.VehicleService;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VehicleControllerTest {
	
	private static String TOKEN = "";
	
	@Autowired
	VehicleService vehicleService;
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	MockMvc mockMvc;
	
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
		
		RegistroDTO registroDTO = new RegistroDTO("neto", "12345", UserRole.ADMIN);
		
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
		AuthenticationDTO dto = new AuthenticationDTO("neto", "12345");
		var usernamePassword = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = this.tokenService.generateToken((User) auth.getPrincipal());
		
		assertNotNull(token);
		TOKEN = token;
	}

	@Test
	@Order(3)
	void mustSaveTheVehcileSuccessfully() throws Exception {
		
		companyRepository.deleteAll();
		
		Company company = new Company(1L, "G3-Tecnology", "51324317000114", null, 
		"(95=8)2200-9003", 30, 20);
		
		companyService.save(company);
		
		VehicleDTO vehicleDTO = new VehicleDTO("Chevrolet", "Onix", "Red", "GTT-7227", "Car", "Parked", company.getId());
		
		String jsonRequest = objectMapper.writeValueAsString(vehicleDTO);
		
		assertEquals(0, vehicleRepository.count());
		
		mockMvc.perform(post("/vehicles/")
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", equalTo(1)))
				.andExpect(jsonPath("$.brand", equalTo("Chevrolet")))
				.andExpect(jsonPath("$.model", equalTo("Onix")))
				.andExpect(jsonPath("$.color", equalTo("Red")))
				.andExpect(jsonPath("$.plate", equalTo("GTT-7227")))
				.andExpect(jsonPath("$.type", equalTo("Car")));
	
		Company companyFinded = companyService.findById(company.getId());
		assertEquals(19, companyFinded.getSpacesForCars());
		assertEquals(1, vehicleRepository.count());
	}
	
	@Test
	@Order(4) 
	void mustFetchAListOfVehiclesSuccessfully() throws Exception {
		
		mockMvc.perform(get("/vehicles")
				.header("Authorization", "Bearer " + TOKEN))
				.andExpect(status().isOk());
	}
	
	@Test
	@Order(5)
	void mustFindForTheVehicleFromTheIdSuccessfully() throws Exception {
		
		mockMvc.perform(get("/vehicles/{id}", 1L)
			.header("Authorization", "Bearer " + TOKEN))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", equalTo(1)))
			.andExpect(jsonPath("$.brand", equalTo("Chevrolet")))
			.andExpect(jsonPath("$.model", equalTo("Onix")))
			.andExpect(jsonPath("$.color", equalTo("Red")))
			.andExpect(jsonPath("$.plate", equalTo("GTT-7227")))
			.andExpect(jsonPath("$.type", equalTo("Car")));
	}
	
	@Test
	@Order(6)
	void mustUpdateTheVehicleSuccessfully() throws Exception {
		
		Long companyId = companyService.findAll().get(0).getId();
		
		Vehicle vehicle = vehicleService.findById(1L);
		
		assertNotEquals("HTJ-1234", vehicle.getPlate());
		
		VehicleDTO vehicleUpdated = new VehicleDTO("Chevrolet", "Onix", "Red", "HTJ-1234", "Car", "Parked", companyId);
		
		String jsonRequest = objectMapper.writeValueAsString(vehicleUpdated);
		
		mockMvc.perform(put("/vehicles/{id}", 1L)
				.header("Authorization", "Bearer " + TOKEN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.plate", equalTo("HTJ-1234")));
		
		Vehicle vehicleFinded = vehicleService.findById(1L);
		
		assertEquals("HTJ-1234", vehicleFinded.getPlate());
		
	}
	
	@Test
	@Order(7)
	void mustChangeStatusToParkSuccessfully() throws Exception {
		
		Long id = vehicleService.findAll().get(0).getId();
		
		Vehicle vehicle = vehicleService.findById(id);
		vehicle.undefined();
		
		assertNotEquals(VehicleStatus.PARKED, vehicle.getStatus());
		
		mockMvc.perform(put("/vehicles/{id}/park", id)
				.header("Authorization", "Bearer " + TOKEN))
				.andExpect(status().isNoContent());
		
		Vehicle vehicleFinded = vehicleRepository.findById(id).get();
		
		assertEquals(VehicleStatus.PARKED, vehicleFinded.getStatus());
	}
	
	@Transactional
	@Test
	@Order(8)
	void mustChangeStatusToNotParkSuccessfully() throws Exception {
		
		Long id = vehicleService.findAll().get(0).getId();
		
		Vehicle vehicle = vehicleService.findById(id);
		
		assertNotEquals(VehicleStatus.NOT_PARKED, vehicle.getStatus());
		
		mockMvc.perform(put("/vehicles/{id}/notPark", id)
				.header("Authorization", "Bearer " + TOKEN))
				.andExpect(status().isNoContent());
		
		Vehicle vehicleFinded = vehicleRepository.findById(id).get();
		
		assertEquals(VehicleStatus.NOT_PARKED, vehicleFinded.getStatus());
	}
	
	@Test
	@Order(9)
	void mustGetSummarySuccessfully() throws Exception {
		
		Long id = vehicleService.findAll().get(0).getId();
		vehicleService.notParkVehicle(id);
		
		mockMvc.perform(get("/vehicles/summary")
			.header("Authorization", "Bearer " + TOKEN))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.numberOfRecords", equalTo(2)))
			.andExpect(jsonPath("$.inputQuantity", equalTo(1)))
			.andExpect(jsonPath("$.outputQuantity", equalTo(1)));
	}
	
	@Test
	@Order(10)
	void mustGetSummaryByVehicleIdSuccessfully() throws Exception {
		
		Long companyId = companyRepository.findAll().get(0).getId();
		
		var vehicleDTO = new VehicleDTO
				("Chevrolet", "Unix", "Black", "CDZ-4321", "Motobike", "Parked", companyId);
		vehicleService.save(vehicleDTO);
		
		Long id = vehicleService.findAll().get(1).getId();
		vehicleService.parkVehicle(id);
		
		mockMvc.perform(get("/vehicles/{id}/summary", 2L)
			.header("Authorization", "Bearer " + TOKEN))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.numberOfRecords", equalTo(1)))
			.andExpect(jsonPath("$.inputQuantity", equalTo(1)))
			.andExpect(jsonPath("$.outputQuantity", equalTo(0)));
	}
	
	@Test
	@Order(11)
	void mustDeleteTheVehicleSuccessfully() throws Exception {
		
		Long id = vehicleService.findAll().get(0).getId();
		
		assertEquals(2, vehicleRepository.count());
		
		mockMvc.perform(delete("/vehicles/{id}", id)
				.header("Authorization", "Bearer " + TOKEN))
				.andExpect(status().isNoContent());
		
		assertEquals(1, vehicleRepository.count());
		
		Long companyId = companyService.findAll().get(0).getId();
		Company company = companyService.findById(companyId);
		assertEquals(20, company.getSpacesForCars());
	}
}
