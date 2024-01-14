package com.wilsonpedro.parking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wilsonpedro.parking.dtos.VehicleDTO;
import com.wilsonpedro.parking.enums.TypeVehicle;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.models.Vehicle;
import com.wilsonpedro.parking.repositories.CompanyRepository;
import com.wilsonpedro.parking.repositories.VehicleRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class VehicleService {

	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	public Vehicle save(VehicleDTO vehicleDTO) {
		Vehicle vehicle = prepareToSave(vehicleDTO);
		return vehicleRepository.save(vehicle);
	}
	 
	private Vehicle prepareToSave(VehicleDTO vehicleDTO) {
		Vehicle vehicle = new Vehicle(vehicleDTO);
		Company company = companyRepository.findById(vehicleDTO.getCompanyId()).get();
		vehicle.setCompany(company);
		vehicle.undefined();
		return vehicle;
	}

	public List<Vehicle> findAll() {
		return vehicleRepository.findAll();
	}
	
	public Vehicle findById(Long id) {
		return vehicleRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
	}

	public Vehicle update(VehicleDTO vehicleDTO, Long id) {
		
		Company company = companyRepository.findById(vehicleDTO.getCompanyId()).get();
		
		return vehicleRepository.findById(id)
				.map(vehicleUpdated -> {
					vehicleUpdated.setId(id);
					vehicleUpdated.setBrand(vehicleDTO.getBrand());
					vehicleUpdated.setModel(vehicleDTO.getModel());
					vehicleUpdated.setColor(vehicleDTO.getColor());
					vehicleUpdated.setPlate(vehicleDTO.getPlate());
					vehicleUpdated.setType(TypeVehicle.toEnum(vehicleDTO.getType()));
					vehicleUpdated.setCompany(company);
					return vehicleRepository.save(vehicleUpdated);
				}).orElseThrow(() -> new EntityNotFoundException());
	}
	
	@Transactional
	public void parkVehicle(Long vehicleId) {
		Vehicle vehicle = findById(vehicleId);
		
		vehicle.park();
		
		vehicleRepository.save(vehicle);
	}

	@Transactional
	public void notParkVehicle(Long vehicleId) {
		Vehicle vehicle = findById(vehicleId);
		
		vehicle.notPark();
		
		vehicleRepository.save(vehicle);
	}

	public void delete(Long id) {
		vehicleRepository.delete(vehicleRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException()));
	}
}
