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
import com.wilsonpedro.parking.dtos.VehicleDTO;
import com.wilsonpedro.parking.enums.VehicleStatus;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.models.Vehicle;
import com.wilsonpedro.parking.repositories.AddressRepository;
import com.wilsonpedro.parking.repositories.VehicleRepository;
import com.wilsonpedro.parking.services.CompanyService;
import com.wilsonpedro.parking.services.VehicleService;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VehicleControllerTest {
	
	@Autowired
	VehicleService vehicleService;
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	MockMvc mockMvc;

	@Test
	@Order(1)
	void mustSaveTheVehcileSuccessfully() throws Exception {
		
		Company company = new Company(null, "WS-Tecnology", "14326422000166", null, 
		"(95)2256-9123", 30, 20);
		
		companyService.save(company);
		
		Long companyId = companyService.findAll().get(0).getId();
		
		VehicleDTO vehicleDTO = new VehicleDTO("Chevrolet", "Onix", "Red", "MTJ-7577", "Car", "Parked", companyId);
		
		String jsonRequest = objectMapper.writeValueAsString(vehicleDTO);
		
		assertEquals(0, vehicleRepository.count());
		
		mockMvc.perform(post("/vehicles/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", equalTo(1)))
				.andExpect(jsonPath("$.brand", equalTo("Chevrolet")))
				.andExpect(jsonPath("$.model", equalTo("Onix")))
				.andExpect(jsonPath("$.color", equalTo("Red")))
				.andExpect(jsonPath("$.plate", equalTo("MTJ-7577")))
				.andExpect(jsonPath("$.type", equalTo("Car")));
	
		assertEquals(1, vehicleRepository.count());
	}
	
	@Test
	@Order(2) 
	void mustFetchAListOfVehiclesSuccessfully() throws Exception {
		
		mockMvc.perform(get("/vehicles"))
				.andExpect(status().isOk());
	}
	
	@Test
	@Order(3)
	void mustFindForTheVehicleFromTheIdSuccessfully() throws Exception {
		
		mockMvc.perform(get("/vehicles/{id}", 1L))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", equalTo(1)))
			.andExpect(jsonPath("$.brand", equalTo("Chevrolet")))
			.andExpect(jsonPath("$.model", equalTo("Onix")))
			.andExpect(jsonPath("$.color", equalTo("Red")))
			.andExpect(jsonPath("$.plate", equalTo("MTJ-7577")))
			.andExpect(jsonPath("$.type", equalTo("Car")));
	}
	
	@Test
	@Order(4)
	void mustUpdateTheVehicleSuccessfully() throws Exception {
		
		Long companyId = companyService.findAll().get(0).getId();
		
		Vehicle vehicle = vehicleService.findById(1L);
		
		assertNotEquals("HTJ-1234", vehicle.getPlate());
		
		VehicleDTO vehicleUpdated = new VehicleDTO("Chevrolet", "Onix", "Red", "HTJ-1234", "Car", "Parked", companyId);
		
		String jsonRequest = objectMapper.writeValueAsString(vehicleUpdated);
		
		mockMvc.perform(put("/vehicles/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.plate", equalTo("HTJ-1234")));
		
		Vehicle vehicleFinded = vehicleService.findById(1L);
		
		assertEquals("HTJ-1234", vehicleFinded.getPlate());
	}
	
	@Test
	@Order(5)
	void mustChangeStatusToParkSuccessfully() throws Exception {
		
		Long id = vehicleService.findAll().get(0).getId();
		
		Vehicle vehicle = vehicleService.findById(id);
		vehicle.undefined();
		
		assertNotEquals(VehicleStatus.PARKED, vehicle.getStatus());
		
		mockMvc.perform(put("/vehicles/{id}/park", id))
					.andExpect(status().isNoContent());
		
		Vehicle vehicleFinded = vehicleRepository.findById(id).get();
		
		assertEquals(VehicleStatus.PARKED, vehicleFinded.getStatus());
	}
	
	@Transactional
	@Test
	@Order(6)
	void mustChangeStatusToNotParkSuccessfully() throws Exception {
		
		Long id = vehicleService.findAll().get(0).getId();
		
		Vehicle vehicle = vehicleService.findById(id);
		
		assertNotEquals(VehicleStatus.NOT_PARKED, vehicle.getStatus());
		
		mockMvc.perform(put("/vehicles/{id}/notPark", id))
					.andExpect(status().isNoContent());
		
		Vehicle vehicleFinded = vehicleRepository.findById(id).get();
		
		assertEquals(VehicleStatus.NOT_PARKED, vehicleFinded.getStatus());
	}
	
	@Test
	@Order(7)
	void mustDeleteTheVehicleSuccessfully() throws Exception {
		
		Long id = vehicleService.findAll().get(0).getId();
		
		assertEquals(1, vehicleRepository.count());
		
		mockMvc.perform(delete("/vehicles/{id}", id))
				.andExpect(status().isNoContent());
		
		assertEquals(0, vehicleRepository.count());
	}
}
