package com.wilsonpedro.parking.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wilsonpedro.parking.dtos.VehicleDTO;
import com.wilsonpedro.parking.enums.TypeVehicle;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.repositories.CompanyRepository;
import com.wilsonpedro.parking.services.CompanyService;
import com.wilsonpedro.parking.services.VehicleService;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
class VehicleExceptionTest {
	
	@Autowired
	VehicleService vehicleService;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Test
	void EntityNotFoundExceptionWhenTryingToFetchVehicle() {
		
		assertThrows(EntityNotFoundException.class, () -> vehicleService.findById(70L));
	}
	
	@Test
	void EntityNotFoundExceptionWhenTryingToUpdateVehicle() {
		
		Company company = new Company(1L, "WS-Tecnology", "14326422000166", null, 
				"(95)2256-9123", 30, 20);
		
		companyRepository.save(company);
		
		VehicleDTO vehicleDTO = new VehicleDTO
				("Chevrolet", "Onix", "Red", "MTJ-7577", "Car", "Parked", company.getId());
		
		assertThrows(EntityNotFoundException.class, () -> vehicleService.update(vehicleDTO, 70L));
	}
	
	@Test
	void EntityNotFoundExceptionWhenTryingToDeleteVehicle() {
		
		assertThrows(EntityNotFoundException.class, () -> vehicleService.delete(70L));
	}

}
