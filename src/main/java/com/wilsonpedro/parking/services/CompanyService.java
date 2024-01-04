package com.wilsonpedro.parking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wilsonpedro.parking.enums.TypeVehicle;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.models.Vehicle;
import com.wilsonpedro.parking.repositories.CompanyRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	VehicleService vehicleService;
	
	public Company save(Company company) {
		return companyRepository.save(company);
	}
	
	public List<Company> findAll() {
		return companyRepository.findAll();
	}
	
	public Company findById(Long id) {
		return companyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}
	
	public Company update(Company company, Long id) {
		return companyRepository.findById(id)
				.map(companyUpdated -> {
					companyUpdated.setId(id);
					companyUpdated.setName(company.getName());
					companyUpdated.setCnpj(company.getCnpj());
					companyUpdated.setAddress(company.getAddress());
					companyUpdated.setPhone(company.getPhone());
					companyUpdated.setSpacesForMotorbikes(company.getSpacesForMotorbikes());
					companyUpdated.setSpacesForCars(company.getSpacesForCars());
					return companyRepository.save(companyUpdated);
				}).orElseThrow(() -> new EntityNotFoundException());
	}
	
	public void delete(Long id) {
		companyRepository.delete(companyRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException()));
	}

	public void parkVehicle(Long companyId, Long vehicleId) {
		Vehicle vehicle = vehicleService.findById(vehicleId);
		Company company = findById(companyId);
		
		company.getVehicles().add(vehicle);
		
		if(vehicle.getType().equals(TypeVehicle.CAR)) {
			company.decrementOneInTheCarSpace();
		} else if(vehicle.getType().equals(TypeVehicle.MOTORBIKE)){
			company.decrementOneInTheMotorbikesSpace();
		}
		
		save(company);
	}
}
