package com.wilsonpedro.parking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wilsonpedro.parking.dtos.CompanyInputDTO;
import com.wilsonpedro.parking.enums.TypeVehicle;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.models.Vehicle;
import com.wilsonpedro.parking.repositories.CompanyRepository;
import com.wilsonpedro.parking.repositories.VehicleRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	VehicleService vehicleService;
	
	@Autowired
	VehicleRepository vehicleRepository;
	
	@Transactional
	public Company save(Company company) {
		return companyRepository.save(company);
	} 
	
	public Company save(CompanyInputDTO companyInputDTO) {
		Company company = new Company(companyInputDTO);
		return save(company);
	}

	public List<Company> findAll() {
		return companyRepository.findAll();
	}
	
	public Company findById(Long id) {
		return companyRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
	}
	
	@Transactional
	public Company update(CompanyInputDTO companyInput, Long id) {
		return companyRepository.findById(id)
				.map(companyUpdated -> {
					companyUpdated.setId(id);
					companyUpdated.setName(companyInput.getName());
					companyUpdated.setCnpj(companyInput.getCnpj());
					companyUpdated.setPhone(companyInput.getPhone());
					companyUpdated.setSpacesForMotorbikes(companyInput.getSpacesForMotorbikes());
					companyUpdated.setSpacesForCars(companyInput.getSpacesForCars());
					return save(companyUpdated);
				}).orElseThrow(() -> new EntityNotFoundException());
	}
	
//	@Transactional
//	public Company update(CompanyInputDTO companyInput, Long id) {
//		return companyRepository.findById(id)
//				.map(companyUpdated -> {
//					companyUpdated.setId(id);
//					companyUpdated.setName(companyInput.getName());
//					companyUpdated.setCnpj(companyInput.getCnpj());
//					companyUpdated.setPhone(companyInput.getPhone());
//					companyUpdated.setSpacesForMotorbikes(companyInput.getSpacesForMotorbikes());
//					companyUpdated.setSpacesForCars(companyInput.getSpacesForCars());
//					return save(companyUpdated);
//				}).orElseThrow(() -> new EntityNotFoundException());
//	}
	
	public void delete(Long id) {
		companyRepository.delete(companyRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException()));
	}

	@Transactional
	public void parkVehicle(Long companyId, Long vehicleId) {
		Vehicle vehicle = vehicleService.findById(vehicleId);
		Company company = findById(companyId);
		
		company.getVehicles().add(vehicle);
		
		vehicle.park();
		
		if(vehicle.getType().equals(TypeVehicle.CAR)) {
			company.decrementOneInTheCarSpace();
		} else if(vehicle.getType().equals(TypeVehicle.MOTORBIKE)){
			company.decrementOneInTheMotorbikesSpace();
		}
		
		vehicle.setCompany(company);
		vehicleRepository.save(vehicle);
		save(company);
	}

	@Transactional
	public void notParkVehicle(Long vehicleId) {
		
		Vehicle vehicle = vehicleService.findById(vehicleId);
		Company company = findById(vehicle.getCompany().getId());
		
		vehicle.notPark();
		
		vehicleRepository.save(vehicle);
		
		save(company);
	}
}
