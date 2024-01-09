package com.wilsonpedro.parking.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wilsonpedro.parking.dtos.VehicleDTO;
import com.wilsonpedro.parking.enums.TypeVehicle;
import com.wilsonpedro.parking.services.VehicleService;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
class VehicleExceptionTest {
	
	@Autowired
	VehicleService vehicleService;
	
	@Test
	void EntityNotFoundExceptionWhenTryingToFetchVehicle() {
		
		assertThrows(EntityNotFoundException.class, () -> vehicleService.findById(70L));
	}
	
	@Test
	void EntityNotFoundExceptionWhenTryingToUpdateVehicle() {
		
		VehicleDTO vehicleDTO = new VehicleDTO("Chevrolet", "Onix", "Red", "MTJ-7577", "Car", "Parked");
		
		assertThrows(EntityNotFoundException.class, () -> vehicleService.update(vehicleDTO, 70L));
	}
	
	@Test
	void EntityNotFoundExceptionWhenTryingToDeleteVehicle() {
		
		assertThrows(EntityNotFoundException.class, () -> vehicleService.delete(70L));
	}

}
