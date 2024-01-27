package com.wilsonpedro.parking.services;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wilsonpedro.parking.dtos.CompanyInputDTO;
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
	
	@Transactional
	public Company save(Company company) {
		validateCompanay(company);
		return companyRepository.save(company);
	}

	public Company save(CompanyInputDTO companyInputDTO) {
		validateCompanay(new Company(companyInputDTO));
		Company company = new Company(companyInputDTO);
		return save(company);
	}

	public List<Company> findAll() {
		return companyRepository.findAll();
	}
	
	public Company findById(Long id) {
		return companyRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
	}
	
	@Transactional
	public Company update(CompanyInputDTO companyInput, Long id) {
		validateUpdate(companyInput, id);
		return companyRepository.findById(id)
				.map(companyUpdated -> {
					companyUpdated.setId(id);
					companyUpdated.setName(companyInput.getName());
					companyUpdated.setCnpj(companyInput.getCnpj());
					companyUpdated.setPhone(companyInput.getPhone());
					companyUpdated.setSpacesForMotorbikes(companyInput.getSpacesForMotorbikes());
					companyUpdated.setSpacesForCars(companyInput.getSpacesForCars());
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
		
		if(vehicle.getType().equals(TypeVehicle.CAR)) {
			company.decrementOneInTheCarSpace();
		} else if(vehicle.getType().equals(TypeVehicle.MOTORBIKE)){
			company.decrementOneInTheMotorbikesSpace();
		}
		
		//update(new CompanyInputDTO(company), companyId);
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
	
	private void validateUpdate(CompanyInputDTO companyInput, Long id) {
		
		if(companyRepository.existsByName(companyInput.getName())) {
			Company company = companyRepository.findByName(companyInput.getName()).get();
			if(!Objects.equals(company.getId(), id))
				throw new ExistingCompanyNameException();
			
		} 
		
		else if (companyRepository.existsByCnpj(companyInput.getCnpj())) {
			Company company = companyRepository.findByCnpj(companyInput.getCnpj()).get();
			if(!Objects.equals(company.getId(), id))
				throw new ExistingCnpjException(company.getCnpj());
			
		} 
		
		else if (companyRepository.existsByPhone(companyInput.getPhone())) {
			Company company = companyRepository.findByPhone(companyInput.getPhone()).get();
			if(!Objects.equals(company.getId(), id))
				throw new ExistingPhoneException();
		}
	}
}
