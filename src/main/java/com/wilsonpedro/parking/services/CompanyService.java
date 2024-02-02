package com.wilsonpedro.parking.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wilsonpedro.parking.enums.TypeVehicle;
import com.wilsonpedro.parking.exceptions.ExistingCnpjException;
import com.wilsonpedro.parking.exceptions.ExistingCompanyNameException;
import com.wilsonpedro.parking.exceptions.ExistingPhoneException;
import com.wilsonpedro.parking.exceptions.NotFoundException;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.models.Vehicle;
import com.wilsonpedro.parking.repositories.CompanyRepository;

import jakarta.transaction.Transactional;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	public Company save(Company company) {
		validateCompanay(company);
		return companyRepository.save(company);
	}

	public List<Company> findAll() {
		return companyRepository.findAll();
	}
	
	public Company findById(Long id) {
		return companyRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
	}
	
	@Transactional
	public Company update(Company company, Long id) {
		validateUpdate(company, id);
		return companyRepository.findById(id)
				.map(companyUpdated -> {
					companyUpdated.setId(id);
					companyUpdated.setName(company.getName());
					companyUpdated.setCnpj(company.getCnpj());
					companyUpdated.setPhone(company.getPhone());
					companyUpdated.setSpacesForMotorbikes(company.getSpacesForMotorbikes());
					companyUpdated.setSpacesForCars(company.getSpacesForCars());
					return companyRepository.save(companyUpdated);
				}).orElseThrow(() -> new NotFoundException(id));
	}
	
	public void delete(Long id) {
		companyRepository.delete(companyRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(id)));
	}
	
	public void addVehicleInVacantSpace(Long companyId, Vehicle vehicle) {
		
		Company company = findById(companyId);
		
		company.getVehicles().add(vehicle);
		
		if(vehicle.isCar()) {
			company.decrementOneInTheCarSpace();
		} else if(vehicle.isMotorbike()){
			company.decrementOneInTheMotorbikesSpace();
		}
		
		companyRepository.save(company);
	}
	
	public void removeVehicleInVacantSpace(Company company, Vehicle vehicle) {
		
		if(vehicle.getType().equals(TypeVehicle.CAR)) {
			company.increaseOneInTheCarSpace();
		} else if(vehicle.getType().equals(TypeVehicle.MOTORBIKE)){
			company.increaseOneInTheMotorbikesSpace();
		}
		
		companyRepository.save(company);
	}
	
	private void validateCompanay(Company company) {
		validateCnpj(company.getCnpj());
		validatePhone(company.getPhone());
		validateName(company.getName());
	}
	
	private void validateCnpj(String cnpj) {
		if(companyRepository.existsByCnpj(cnpj))
			throw new ExistingCnpjException(cnpj);
	}
	
	private void validatePhone(String phone) {
		if(companyRepository.existsByPhone(phone))
			throw new ExistingPhoneException();
	}
	
	private void validateName(String name) {
		if(companyRepository.existsByName(name))
			throw new ExistingCompanyNameException();
	}
	
	private void validateUpdate(Company company, Long id) {
		
		if(companyRepository.existsByName(company.getName())) {
			Company companyFinded = companyRepository.findByName(company.getName()).get();
			if(!Objects.equals(companyFinded.getId(), id))
				throw new ExistingCompanyNameException();
			
		} 
		
		else if (companyRepository.existsByCnpj(company.getCnpj())) {
			Company companyFinded = companyRepository.findByCnpj(company.getCnpj()).get();
			if(!Objects.equals(companyFinded.getId(), id))
				throw new ExistingCnpjException(companyFinded.getCnpj());
			
		} 
		
		else if (companyRepository.existsByPhone(company.getPhone())) {
			Company companyFinded = companyRepository.findByPhone(company.getPhone()).get();
			if(!Objects.equals(companyFinded.getId(), id))
				throw new ExistingPhoneException();
		}
	}
}
