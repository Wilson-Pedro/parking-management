package com.wilsonpedro.parking.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wilsonpedro.parking.dtos.VehicleDTO;
import com.wilsonpedro.parking.enums.TypeVehicle;
import com.wilsonpedro.parking.enums.VehicleStatus;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.models.Vehicle;
import com.wilsonpedro.parking.repositories.CompanyRepository;
import com.wilsonpedro.parking.repositories.VehicleRepository;
import com.wilsonpedro.parking.services.VehicleService;

@SpringBootTest
class VehicleExceptionTest {
	
	@Autowired
	VehicleService vehicleService;
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Test
	void ExistingPlateExceptionWhenTryingToSaveVehicle() {
		
		Company company = new Company(1L, "WS-Tecnology", "14326422000166", null, 
				"(95)2256-9123", 30, 20);
		
		companyRepository.save(company);
		
		vehicleRepository.save(new Vehicle
				(1L, "Chevrolet", "Onix", "Red", "MTJ-7577", TypeVehicle.CAR, VehicleStatus.PARKED));
		
		VehicleDTO vehicleDTO = new VehicleDTO
				("Chevrolet", "Unix", "Black", "MTJ-7577", "Motobike", "Parked", company.getId());
		
		assertThrows(ExistingPlateException.class, () -> vehicleService.save(vehicleDTO));
		
	}
	
	@Test
	void EntityNotFoundExceptionWhenTryingToFetchVehicle() {
		
		assertThrows(NotFoundException.class, () -> vehicleService.findById(70L));
	}
	
	@Test
	void EntityNotFoundExceptionWhenTryingToUpdateVehicle() {
		
		Company company = new Company(1L, "WS-Tecnology", "14326422000166", null, 
				"(95)2256-9123", 30, 20);
		
		companyRepository.save(company);
		
		VehicleDTO vehicleDTO = new VehicleDTO
				("Chevrolet", "Onix", "Red", "MTJ-7577", "Car", "Parked", company.getId());
		
		assertThrows(NotFoundException.class, () -> vehicleService.update(vehicleDTO, 70L));
	}
	
	@Test
	void EntityNotFoundExceptionWhenTryingToDeleteVehicle() {
		
		assertThrows(NotFoundException.class, () -> vehicleService.delete(70L));
	}

}
