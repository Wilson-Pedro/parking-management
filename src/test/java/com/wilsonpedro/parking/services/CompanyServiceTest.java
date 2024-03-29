package com.wilsonpedro.parking.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wilsonpedro.parking.enums.TypeVehicle;
import com.wilsonpedro.parking.enums.VehicleStatus;
import com.wilsonpedro.parking.models.Address;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.models.Vehicle;
import com.wilsonpedro.parking.repositories.AddressRepository;
import com.wilsonpedro.parking.repositories.CompanyRepository;
import com.wilsonpedro.parking.repositories.VehicleRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompanyServiceTest {
	
	@Autowired
	CompanyService companyService;
	
	@Autowired
	CompanyRepository companyRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	@Autowired
	VehicleService vehicleService;
	
	Vehicle vehicle;
	
	@BeforeEach
	void setup() {
		vehicle = new Vehicle(1L, "Chevrolet", "Onix", "Red", "DDD-4444", TypeVehicle.CAR, VehicleStatus.UNDEFINED);
	}

	@Test
	@Order(1)
	void mustSaveTheCompanySuccessfully() {
		
		companyRepository.deleteAll();
		
		Address address = new Address();
		address.setId(null);
		address.setCep("54320-151");
		address.setStreet("Rua das Ameixas");
		address.setNeighborhood("Flores");
		address.setCity("Minas-Gerais");
		
		Company company = new Company();
		company.setId(null);
		company.setName("WX-Tecnology");
		company.setCnpj("14326422000166");
		company.setAddress(address);
		company.setPhone("(95)3456-7413");
		company.setSpacesForMotorbikes(30);
		company.setSpacesForCars(20);
		
		assertEquals(0, this.companyRepository.count());
		
		addressRepository.save(address);
		Company companySaved = this.companyService.save(company);
		
		assertNotNull(companySaved.getId());
		assertEquals(1, this.companyRepository.count());
	}
	
	@Test
	@Order(2)
	void mustFetchAListOfProductsSuccessfully() {
		
		List<Company> list = this.companyService.findAll();
		
		assertEquals(1, list.size());
		assertEquals(list.size(), this.companyRepository.count());
	}

	@Test
	@Order(3)
	void mustFindForTheCompanyFromThetIdSuccessfully() {
		
		Long id = companyService.findAll().get(0).getId();
		Company company = this.companyService.findById(id);
		
		assertNotNull(company);
		assertEquals(id, company.getId());
		assertEquals("WX-Tecnology", company.getName());
		assertEquals("14326422000166", company.getCnpj());
		assertEquals("(95)3456-7413", company.getPhone());
		assertEquals(30, company.getSpacesForMotorbikes());
		assertEquals(20, company.getSpacesForCars());
		
	}
	
	@Test
	@Order(4)
	void mustUpdateTheCompanySuccessfully() {
		
		Long id = companyService.findAll().get(0).getId();
		Company company = this.companyService.findById(id);
		
		assertNotEquals("(95)2256-3413", company.getPhone());
		company.setPhone("(95)2256-3413");
		
		Company companyUpdated = this.companyService.update(company, id);
		
		assertEquals("(95)2256-3413", companyUpdated.getPhone());
		
	}
	
	@Transactional
	@Test
	@Order(5)
	void mustAddAVehicleToTheParkingSpaceSuccessfully() {
		
		vehicleRepository.save(vehicle);
		
		Long id = companyService.findAll().get(0).getId();
		Company company = this.companyService.findById(id);
		
		assertEquals(20, company.getSpacesForCars());
		
		companyService.addVehicleInVacantSpace(id, vehicle);
		
		assertEquals(19, company.getSpacesForCars());
	}
	
	@Test
	@Order(6) 
	void mustRemoveAVehicleToTheParkingSpaceSuccessfully() {
		
		vehicleRepository.save(vehicle);
		
		Long id = companyService.findAll().get(0).getId();
		Company company = this.companyService.findById(id);
		
		company.decrementOneInTheCarSpace();
		
		assertEquals(19, company.getSpacesForCars());
		
		companyService.removeVehicleInVacantSpace(company, vehicle);
		
		assertEquals(20, company.getSpacesForCars());
	}
	
	@Test
	@Order(7)
	void mustDeleteTheCompanySuccessfully() {
		
		Long companyId = companyService.findAll().get(0).getId();
		
		assertEquals(1, companyRepository.count());
		
		this.companyService.delete(companyId);
		
		assertEquals(0, companyRepository.count());
	}
}
