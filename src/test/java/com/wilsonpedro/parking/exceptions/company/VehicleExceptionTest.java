package com.wilsonpedro.parking.exceptions.company;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wilsonpedro.parking.enums.TypeVehicle;
import com.wilsonpedro.parking.models.Vehicle;
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
		
		Vehicle vehicle = new Vehicle(1L, "Chevrolet", "Onix", "Red", "MTJ-7577", TypeVehicle.CAR);
		
		assertThrows(EntityNotFoundException.class, () -> vehicleService.update(vehicle, 70L));
	}
	
	@Test
	void EntityNotFoundExceptionWhenTryingToDeleteVehicle() {
		
		assertThrows(EntityNotFoundException.class, () -> vehicleService.delete(70L));
	}

}
