package com.wilsonpedro.parking.exceptions;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.wilsonpedro.parking.dtos.CompanyInputDTO;
import com.wilsonpedro.parking.services.CompanyService;

@SpringBootTest
class CompanyExceptionTest {
	
	@Autowired
	CompanyService companyService;
	
	@Test
	void ExistingCompanyNameExceptionWhenTryingToSaveCompany() {
		
		companyService.save(new CompanyInputDTO
				("WS-Tecnology", "54077018000110", "(95)2464-7378", 30, 20));
		
		CompanyInputDTO company = new CompanyInputDTO("WS-Tecnology", "14708332000130", 
				"(95)2754-1102", 30, 20);
		
		assertThrows(ExistingCompanyNameException.class, () -> companyService.save(company));
	}
	
	@Test
	void ExistingPhoneExceptionWhenTryingToSaveCompany() {
		
		companyService.save(new CompanyInputDTO
				("C3-Tecnology", "85300119000132", "(95)2256-9123", 30, 20));
		
		CompanyInputDTO company = new CompanyInputDTO("RDS-Tecnology", "25931995000135", 
				"(95)2256-9123", 30, 20);
		
		assertThrows(ExistingPhoneException.class, () -> companyService.save(company));
	}
	
	@Test
	void ExistingCnpjExceptionWhenTryingToSaveCompany() {
		
		companyService.save(new CompanyInputDTO
				("WA-Tecnology", "14326422000166", "(79)3666-6386", 30, 20));
		
		CompanyInputDTO company = new CompanyInputDTO("WB-Tecnology", "14326422000166", 
				"(88)2511-4922", 30, 20);
		
		assertThrows(ExistingCnpjException.class, () -> companyService.save(company));
	}

	@Test
	void EntityNotFoundExceptionWhenTryingToFetchCompany() {
		
		assertThrows(NotFoundException.class, () -> companyService.findById(70L));
	}
	
	@Test
	void EntityNotFoundExceptionWhenTryingToUpdateCompany() {
		
		CompanyInputDTO company = new CompanyInputDTO("WS-Tecnology", "14326422000166", 
				"(95)2256-9123", 30, 20);
		
		assertThrows(NotFoundException.class, () -> companyService.update(company, 70L));
	}

	@Test
	void EntityNotFoundExceptionWhenTryingToDeleteCompany() {
		
		assertThrows(NotFoundException.class, () -> companyService.delete(70L));
		
	}
}
