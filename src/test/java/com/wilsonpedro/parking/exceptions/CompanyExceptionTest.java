package com.wilsonpedro.parking.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.services.CompanyService;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompanyExceptionTest {
	
	@Autowired
	CompanyService companyService;
	
	@Test
	@Order(1)
	void ExistingCompanyNameExceptionWhenTryingToSaveCompany() {
		
		companyService.save(new Company
				(null, "WS-Tecnology", "54077018000110", null, "(95)2464-7378", 30, 20));
		
		Company company = new Company(null, "WS-Tecnology", "14708332000130", null, 
				"(95)2754-1102", 30, 20);
		
		assertThrows(ExistingCompanyNameException.class, () -> companyService.save(company));
	}
	
	@Test
	@Order(2)
	void ExistingCompanyNameExceptionWhenTryingToUpdateCompany() {
		
		companyService.save(new Company
				(null, "WA-Tecnology", "10077018000888", null, "(91)2754-1002", 30, 20));
		
		Long id = companyService.findAll().get(0).getId();
		Company company = companyService.findById(id);
		company.setName("WS-Tecnology");
		
		assertThrows(ExistingCompanyNameException.class, 
				() -> companyService.update(company, 2L));
	}
	
	@Test
	@Order(3)
	void ExistingCnpjExceptionWhenTryingToSaveCompany() {
		
		companyService.save(new Company
				(null, "WB-Tecnology", "14326422000166", null, "(79)3666-6386", 30, 20));
		
		Company company = new Company(null, "WB-Tecnology", "14326422000166", null,
				"(88)2511-4922", 30, 20);
		
		assertThrows(ExistingCnpjException.class, () -> companyService.save(company));
	}
	
	@Test
	@Order(4)
	void ExistingCnpjExceptionWhenTryingToUpdateCompany() {
		
		companyService.save(new Company
				(null, "Azure-Tecnology", "77700779000132", null, "(95)7070-7003", 30, 20));
		
		Long id = companyService.findAll().get(0).getId();
		Company company = companyService.findById(id);
		company.setName("SWA-Tecnology");
		company.setCnpj("77700779000132");
		
		assertThrows(ExistingCnpjException.class, 
				() -> companyService.update(company, 2L));
	}
	
	@Test
	@Order(5)
	void ExistingPhoneExceptionWhenTryingToSaveCompany() {
		
		companyService.save(new Company
				(null, "C3-Tecnology", "85300119000132", null, "(95)2256-9123", 30, 20));
		
		Company company = new Company(null, "RDS-Tecnology", "25931995000135", null,
				"(95)2256-9123", 30, 20);
		
		assertThrows(ExistingPhoneException.class, () -> companyService.save(company));
	}
	
	@Test
	@Order(6)
	void ExistingPhoneExceptionWhenTryingToUpdateCompany() {
		
		companyService.save(new Company
				(null, "AWS-Tecnology", "11100119000132", null, "(95)1010-9003", 30, 20));
		
		Long id = companyService.findAll().get(0).getId();
		Company company = companyService.findById(id);
		company.setName("SWA-Tecnology");
		company.setCnpj("45600999000222");
		
		company.setPhone("(95)1010-9003");
		
		assertThrows(ExistingPhoneException.class, 
				() -> companyService.update(company, 2L));
	}

	@Test
	@Order(7)
	void EntityNotFoundExceptionWhenTryingToFetchCompany() {
		
		assertThrows(NotFoundException.class, () -> companyService.findById(70L));
	}
	
	@Test
	@Order(8)
	void EntityNotFoundExceptionWhenTryingToUpdateCompany() {
		
		Company company = new Company(null, "WY-Tecnology", "12226422000166", null,
				"(98)2006-9123", 30, 20);
		
		assertThrows(NotFoundException.class, () -> companyService.update(company, 70L));
	}

	@Test
	@Order(9)
	void EntityNotFoundExceptionWhenTryingToDeleteCompany() {
		
		assertThrows(NotFoundException.class, () -> companyService.delete(70L));
		
	}
}
