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
	void ParkedExceptionWhenTryingToParkTheVehicle() {
		
		Company company = new Company(1L, "AA-Tecnology", "14310444000166", null, 
				"(95)1122-9123", 30, 20);
		
		companyRepository.save(company);
		
		vehicleRepository.save(new Vehicle
				(1L, "Chevrolet", "Onix", "Red", "DEF-2222", TypeVehicle.CAR, VehicleStatus.PARKED));
		
		Long id = vehicleService.findAll().get(0).getId();
		
		assertThrows(ParkedException.class, () -> vehicleService.parkVehicle(id));
		
	}
	
	@Test
	void NotParkedExceptionWhenTryingToParkTheVehicle() {
		
		Company company = new Company(1L, "AA-Tecnology", "22220444000166", null, 
				"(98)1122-1111", 30, 20);
		
		companyRepository.save(company);
		
		Vehicle vehicle = new Vehicle
				(2L, "Chevrolet", "Onix", "Red", "DMC-3145", TypeVehicle.CAR, VehicleStatus.NOT_PARKED);
		
		vehicleRepository.save(vehicle);
		
		assertThrows(ParkedException.class, () -> vehicleService.notParkVehicle(vehicle.getId()));
		
	}
	
	@Test
	void LimitOfSpacesExceptionWhenTryingToSaveACar() {
		
		Company company = new Company(1L, "WS-Tecnology", "60221122000333", null, 
				"(95)2256-9123", 30, 0);
		
		companyRepository.save(company);
		
		VehicleDTO vehicleDTO = new VehicleDTO
				("Chevrolet", "Unix", "Black", "FFJ-1121", "Car", "Parked", company.getId());
		
		assertThrows(LimitOfSpacesException.class, () -> vehicleService.save(vehicleDTO));
		
	}
	
	@Test
	void LimitOfSpacesExceptionWhenTryingToSaveAMotobike() {
		
		Company company = new Company(1L, "WS-Tecnology", "60228899000777", null, 
				"(95)2006-9003", 0, 20);
		
		companyRepository.save(company);
		
		VehicleDTO vehicleDTO = new VehicleDTO
				("Chevrolet", "Unix", "Black", "HHJ-8888", "Motobike", "Parked", company.getId());
		
		assertThrows(LimitOfSpacesException.class, () -> vehicleService.save(vehicleDTO));
		
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
