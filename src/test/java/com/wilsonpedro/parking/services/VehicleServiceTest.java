package com.wilsonpedro.parking.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wilsonpedro.parking.enums.TypeVehicle;
import com.wilsonpedro.parking.models.Address;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.models.Vehicle;
import com.wilsonpedro.parking.repositories.AddressRepository;
import com.wilsonpedro.parking.repositories.VehicleRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VehicleServiceTest {
	
	@Autowired
	VehicleService vehicleService;
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	AddressRepository addressRepository;

	@Test
	@Order(1)
	void mustSaveTheVehicleSuccessfully() {
		
		Vehicle vehicle = new Vehicle();
		vehicle.setId(null);
		vehicle.setBrand("Chevrolet");
		vehicle.setModel("Onix");
		vehicle.setColor("Red");
		vehicle.setPlate("HZN-8845");
		vehicle.setType(TypeVehicle.CAR);
		
		assertEquals(0, vehicleRepository.count());
		
		Vehicle vehicleSaved = vehicleService.save(vehicle);
		
		assertNotNull(vehicleSaved.getId());
		assertEquals(1, vehicleRepository.count());	
	}
	
	@Test
	@Order(2) 
	void mustFetchAListOfVehiclesSuccessFully() {
		
		List<Vehicle> list = vehicleService.findAll();
		
		assertNotNull(list);
		assertEquals(list.size(), vehicleRepository.count());
	}
	
	@Test
	@Order(3)
	void mustFindForTheVehicleFromTheIdSuccessfully() {
		
		Long id = vehicleService.findAll().get(0).getId();
		
		Vehicle vehicle = vehicleService.findById(id);
		
		assertNotNull(vehicle);
		assertEquals(id, vehicle.getId());
		assertEquals("Chevrolet", vehicle.getBrand());
		assertEquals("Onix", vehicle.getModel());
		assertEquals("Red", vehicle.getColor());
		assertEquals("HZN-8845", vehicle.getPlate());
		assertEquals(TypeVehicle.CAR, vehicle.getType());
	}
	
	@Test
	@Order(4)
	void mustUpdateTheVehicleSuccessfully() {
		
		Long id = vehicleService.findAll().get(0).getId();
		
		Vehicle vehicle = vehicleService.findById(id);
		
		assertNotEquals("MTJ-7577", vehicle.getPlate());
		vehicle.setPlate("MTJ-7577");
		
		Vehicle vehicleUpdated = vehicleService.update(vehicle, id);
		
		assertEquals("MTJ-7577", vehicleUpdated.getPlate());
	}
	
	@Test
	@Order(5)
	void mustDeleteTheVehicleSuccessfully() {
		
		Long id = vehicleService.findAll().get(0).getId();
		
		assertEquals(1, vehicleRepository.count());
		
		vehicleService.delete(id);
		
		assertEquals(0, vehicleRepository.count());
	}
}
