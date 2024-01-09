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

import com.wilsonpedro.parking.dtos.VehicleDTO;
import com.wilsonpedro.parking.enums.TypeVehicle;
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
		
		VehicleDTO vehicleDTO = new VehicleDTO();
		vehicleDTO.setId(null);
		vehicleDTO.setBrand("Chevrolet");
		vehicleDTO.setModel("Onix");
		vehicleDTO.setColor("Red");
		vehicleDTO.setPlate("HZN-8845");
		vehicleDTO.setType("Car");
		vehicleDTO.setStatus("Parked");
		
		assertEquals(0, vehicleRepository.count());
		
		Vehicle vehicleSaved = vehicleService.save(vehicleDTO);
		
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
		
		VehicleDTO vehicleDTO = new VehicleDTO(vehicle);
		
		Vehicle vehicleUpdated = vehicleService.update(vehicleDTO, id);
		
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
