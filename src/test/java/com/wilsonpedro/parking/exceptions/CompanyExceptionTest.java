package com.wilsonpedro.parking.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wilsonpedro.parking.dtos.CompanyInputDTO;
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
		
		companyService.save(new CompanyInputDTO
				("WS-Tecnology", "54077018000110", "(95)2464-7378", 30, 20));
		
		CompanyInputDTO company = new CompanyInputDTO("WS-Tecnology", "14708332000130", 
				"(95)2754-1102", 30, 20);
		
		assertThrows(ExistingCompanyNameException.class, () -> companyService.save(company));
	}
	
	@Test
	@Order(2)
	void ExistingCompanyNameExceptionWhenTryingToUpdateCompany() {
		
		companyService.save(new CompanyInputDTO
				("WA-Tecnology", "10077018000888", "(91)2754-1002", 30, 20));
		
		Long id = companyService.findAll().get(0).getId();
		Company company = companyService.findById(id);
		company.setName("WS-Tecnology");
		
		CompanyInputDTO dto = new CompanyInputDTO(company);
		
		assertThrows(ExistingCompanyNameException.class, 
				() -> companyService.update(dto, 2L));
	}
	
	@Test
	@Order(3)
	void ExistingCnpjExceptionWhenTryingToSaveCompany() {
		
		companyService.save(new CompanyInputDTO
				("WB-Tecnology", "14326422000166", "(79)3666-6386", 30, 20));
		
		CompanyInputDTO company = new CompanyInputDTO("WB-Tecnology", "14326422000166", 
				"(88)2511-4922", 30, 20);
		
		assertThrows(ExistingCnpjException.class, () -> companyService.save(company));
	}
	
	@Test
	@Order(4)
	void ExistingCnpjExceptionWhenTryingToUpdateCompany() {
		
		companyService.save(new CompanyInputDTO
				("Azure-Tecnology", "77700779000132", "(95)7070-7003", 30, 20));
		
		Long id = companyService.findAll().get(0).getId();
		Company company = companyService.findById(id);
		company.setName("SWA-Tecnology");
		company.setCnpj("77700779000132");
		
		CompanyInputDTO dto = new CompanyInputDTO(company);
		
		assertThrows(ExistingCnpjException.class, 
				() -> companyService.update(dto, 2L));
	}
	
	@Test
	@Order(5)
	void ExistingPhoneExceptionWhenTryingToSaveCompany() {
		
		companyService.save(new CompanyInputDTO
				("C3-Tecnology", "85300119000132", "(95)2256-9123", 30, 20));
		
		CompanyInputDTO company = new CompanyInputDTO("RDS-Tecnology", "25931995000135", 
				"(95)2256-9123", 30, 20);
		
		assertThrows(ExistingPhoneException.class, () -> companyService.save(company));
	}
	
	@Test
	@Order(6)
	void ExistingPhoneExceptionWhenTryingToUpdateCompany() {
		
		companyService.save(new CompanyInputDTO
				("AWS-Tecnology", "11100119000132", "(95)1010-9003", 30, 20));
		
		Long id = companyService.findAll().get(0).getId();
		Company company = companyService.findById(id);
		company.setName("SWA-Tecnology");
		company.setCnpj("45600999000222");
		
		company.setPhone("(95)1010-9003");
		
		CompanyInputDTO dto = new CompanyInputDTO(company);
		
		assertThrows(ExistingPhoneException.class, 
				() -> companyService.update(dto, 2L));
	}

	@Test
	@Order(7)
	void EntityNotFoundExceptionWhenTryingToFetchCompany() {
		
		assertThrows(NotFoundException.class, () -> companyService.findById(70L));
	}
	
	@Test
	@Order(8)
	void EntityNotFoundExceptionWhenTryingToUpdateCompany() {
		
		CompanyInputDTO company = new CompanyInputDTO("WY-Tecnology", "12226422000166", 
				"(98)2006-9123", 30, 20);
		
		assertThrows(NotFoundException.class, () -> companyService.update(company, 70L));
	}

	@Test
	@Order(9)
	void EntityNotFoundExceptionWhenTryingToDeleteCompany() {
		
		assertThrows(NotFoundException.class, () -> companyService.delete(70L));
		
	}
}
