package com.wilsonpedro.parking.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wilsonpedro.parking.enums.EntranceAndExit;
import com.wilsonpedro.parking.enums.TypeVehicle;
import com.wilsonpedro.parking.enums.VehicleStatus;
import com.wilsonpedro.parking.models.Register;
import com.wilsonpedro.parking.models.Vehicle;
import com.wilsonpedro.parking.repositories.RegisterRepository;
import com.wilsonpedro.parking.repositories.VehicleRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RegisterServiceTest {
	
	@Autowired
	RegisterService registerService;
	
	@Autowired
	RegisterRepository registerRepository;
	
	@Autowired
	VehicleRepository vehicleRepository;

	@Test
	@Order(1)
	void mustSaveTheRegisterSuccessfully() {
		assertEquals(0, registerRepository.count());
		
		Vehicle vehicle = new Vehicle(1L, "Chevrolet", "Onix", "Green", "HLM-8822", TypeVehicle.MOTORBIKE, VehicleStatus.UNDEFINED);
		vehicle.park();
		vehicleRepository.save(vehicle);
		
		Register register = registerService.save(vehicle);
		
		assertEquals(EntranceAndExit.ENTRANCE, register.getEntranceAndExit());
		assertEquals(vehicle, register.getVehicle());
		
		assertEquals(1, registerRepository.count());
	}

}
