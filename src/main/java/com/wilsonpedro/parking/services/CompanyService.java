package com.wilsonpedro.parking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.repositories.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	public Company save(Company company) {
		return companyRepository.save(company);
	}
}
