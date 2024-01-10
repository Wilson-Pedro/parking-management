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
import com.wilsonpedro.parking.dtos.ParkDTO;
import com.wilsonpedro.parking.dtos.VehicleDTO;
import com.wilsonpedro.parking.enums.TypeVehicle;
import com.wilsonpedro.parking.enums.VehicleStatus;
import com.wilsonpedro.parking.models.Address;
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
		
		VehicleDTO vehicleDTO = new VehicleDTO("Chevrolet", "Onix", "Red", "MTJ-7577", "Car", "Parked");
		
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
				.andExpect(jsonPath("$.type", equalTo("CAR")));
	
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
		.andExpect(jsonPath("$.type", equalTo("CAR")));
	}
	
	@Test
	@Order(4)
	void mustUpdateTheVehicleSuccessfully() throws Exception {
		
		Vehicle vehicle = vehicleService.findById(1L);
		
		assertNotEquals("HTJ-1234", vehicle.getPlate());
		
		VehicleDTO vehicleUpdated = new VehicleDTO("Chevrolet", "Onix", "Red", "HTJ-1234", "Car", "Parked");
		
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
	void mustAddAVehicleToTheParkingSpaceSuccessfully() throws Exception {
		
		Address address = new Address(null, "96520-190", "Rua das Ameixas", "Flores", "Minas-Gerais");
		
		Company company = new Company(null, "GG-Tecnology", "26826433000977", address, 
				"(95)2776-9001", 30, 20);
		
		Vehicle vehicle = vehicleService.findById(1L);
		
		addressRepository.save(address);
		companyService.save(company);
		
		Long empresaId = companyService.findAll().get(0).getId();
		
		ParkDTO parkDto = new ParkDTO(empresaId);
		
		String jsonRequest = objectMapper.writeValueAsString(parkDto);
		
		mockMvc.perform(put("/vehicles/{id}/park", vehicle.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest))
				.andExpect(status().isNoContent());
		
		Company companyFinded = companyService.findById(empresaId);
		
		assertEquals(19, companyFinded.getSpacesForCars());
		
		addressRepository.delete(address);
		companyService.delete(companyFinded.getId());
		
	}
	
	@Transactional
	@Test
	@Order(6)
	void mustRemoveAVehicleToTheParkingSpaceSuccessfully() throws Exception {
		
		Company company = new Company(null, "GG-Tecnology", "84846833000122", null, 
				"(95)2776-9001", 30, 20);
		
		Address address = new Address(null, "12020-020", "Rua das Pétalas", "Goiabas", "Minas-Gerais");
			
		Vehicle vehicle = new Vehicle(2L, "Chevrolet", "Onix", "Red", "MTJ-7577", TypeVehicle.CAR, VehicleStatus.PARKED);
		vehicle.setCompany(company);
		
		companyService.save(company);
		addressRepository.save(address);
		vehicleRepository.save(vehicle);
		
		Long id = vehicleService.findAll().get(0).getId();
		
		assertNotEquals(VehicleStatus.NOT_PARKED, vehicle.getStatus());
		
		mockMvc.perform(put("/vehicles/{id}/notPark", id))
					.andExpect(status().isNoContent());
		
		Vehicle vehicleFinded = vehicleRepository.findById(id).get();
		
		assertEquals(VehicleStatus.NOT_PARKED, vehicleFinded.getStatus());
	}
	
	@Test
	@Order(7)
	void mustDeleteTheVehicleSuccessfully() throws Exception {
		
		Vehicle vehicle = new Vehicle(2L, "Chevrolet", "Onix", "Red", "MTJ-7577", TypeVehicle.CAR, VehicleStatus.PARKED);
		
		vehicleRepository.save(vehicle);
		
		assertEquals(1, vehicleRepository.count());
		
		mockMvc.perform(delete("/vehicles/{id}", vehicle.getId()))
				.andExpect(status().isNoContent());
		
		assertEquals(0, vehicleRepository.count());
	}
}
