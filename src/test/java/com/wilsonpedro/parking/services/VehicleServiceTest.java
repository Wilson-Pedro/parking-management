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

import com.wilsonpedro.parking.dtos.Summary;
import com.wilsonpedro.parking.dtos.VehicleDTO;
import com.wilsonpedro.parking.enums.TypeVehicle;
import com.wilsonpedro.parking.enums.VehicleStatus;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.models.Vehicle;
import com.wilsonpedro.parking.repositories.CompanyRepository;
import com.wilsonpedro.parking.repositories.VehicleRepository;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VehicleServiceTest {
	
	@Autowired
	VehicleService vehicleService;
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	CompanyRepository companyRepository;

	@Test
	@Order(1)
	void mustSaveTheVehicleSuccessfully() {
		
		vehicleRepository.deleteAll();
		
		Company company = new Company(null, "WS-Tecnology", "14326422000166", null, 
		"(95)2256-9123", 30, 20);
		
		companyRepository.save(company);
		
		Long companyId = companyRepository.findAll().get(0).getId();
		
		VehicleDTO vehicleDTO = new VehicleDTO();
		vehicleDTO.setId(null);
		vehicleDTO.setBrand("Chevrolet");
		vehicleDTO.setModel("Onix");
		vehicleDTO.setColor("Red");
		vehicleDTO.setPlate("HZN-8845");
		vehicleDTO.setType("Car");
		vehicleDTO.setStatus("Undefined");
		vehicleDTO.setCompanyId(companyId);
		
		assertEquals(0, vehicleRepository.count());
		
		Vehicle vehicleSaved = vehicleService.save(vehicleDTO);
		Company companyFinded = companyRepository.findById(companyId).get();
		
		assertNotNull(vehicleSaved.getId());
		assertEquals(1, vehicleRepository.count());
		assertEquals(19, companyFinded.getSpacesForCars());
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
		assertEquals(VehicleStatus.UNDEFINED, vehicle.getStatus());
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
	void mustChangeVehicleStatusToParkSuccessfully() {
		
		Long id = vehicleService.findAll().get(0).getId();
		
		Vehicle vehicle = vehicleService.findById(id);
		
		assertNotEquals(VehicleStatus.PARKED, vehicle.getStatus());
		
		vehicleService.parkVehicle(vehicle.getId());
		
		Vehicle vehicleFinded = vehicleRepository.findById(vehicle.getId()).get();
		
		assertEquals(VehicleStatus.PARKED, vehicleFinded.getStatus());
	}
	
	@Test
	@Order(6)
	void mustChangeVehicleStatusToNotParkSuccessfully() {
		
		Long id = vehicleService.findAll().get(0).getId();
		
		Vehicle vehicle = vehicleService.findById(id);
		
		assertNotEquals(VehicleStatus.NOT_PARKED, vehicle.getStatus());
		
		vehicleService.notParkVehicle(vehicle.getId());
		
		Vehicle vehicleFinded = vehicleRepository.findById(vehicle.getId()).get();
		
		assertEquals(VehicleStatus.NOT_PARKED, vehicleFinded.getStatus());
	}
	
	@Test
	@Order(7)
	void mustGetSummaryTest() {
		
		Long id = vehicleService.findAll().get(0).getId();
		vehicleService.parkVehicle(id);
		
		Summary summary = vehicleService.getSummary();
		
		assertEquals(3, summary.getNumberOfRecords());
		assertEquals(2, summary.getInputQuantity());
		assertEquals(1, summary.getOutputQuantity());
	}
	
	@Test
	@Order(8)
	void mustGetSummaryByVehicleIdTest() {
		
		Long companyId = companyRepository.findAll().get(0).getId();
		
		var vehicleDTO = new VehicleDTO
				("Chevrolet", "Unix", "Black", "ABC-1234", "Motobike", "Parked", companyId);
		vehicleService.save(vehicleDTO);
		
		Long id = vehicleService.findAll().get(1).getId();
		vehicleService.parkVehicle(id);
		
		Summary summary = vehicleService.getSummaryByVehicleId(id);
		
		assertEquals(1, summary.getNumberOfRecords());
		assertEquals(1, summary.getInputQuantity());
		assertEquals(0, summary.getOutputQuantity());
	}
	
	@Test
	@Order(9)
	void mustDeleteTheVehicleSuccessfully() {
		
		Long id = vehicleService.findAll().get(0).getId();
		Vehicle  vehicle = vehicleService.findById(id);
		
		assertEquals(2, vehicleRepository.count());	
		vehicleService.delete(id);
		assertEquals(1, vehicleRepository.count());
		
		Company company = companyRepository.findById(vehicle.getCompany().getId()).get();
		assertEquals(20, company.getSpacesForCars());
	}
}
