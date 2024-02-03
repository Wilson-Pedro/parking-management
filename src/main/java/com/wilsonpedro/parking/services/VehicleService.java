package com.wilsonpedro.parking.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wilsonpedro.parking.dtos.RegisterDTO;
import com.wilsonpedro.parking.dtos.Summary;
import com.wilsonpedro.parking.enums.EntranceAndExit;
import com.wilsonpedro.parking.enums.TypeVehicle;
import com.wilsonpedro.parking.enums.VehicleStatus;
import com.wilsonpedro.parking.exceptions.ExistingPlateException;
import com.wilsonpedro.parking.exceptions.LimitOfSpacesException;
import com.wilsonpedro.parking.exceptions.NotFoundException;
import com.wilsonpedro.parking.exceptions.ParkedException;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.models.Vehicle;
import com.wilsonpedro.parking.repositories.CompanyRepository;
import com.wilsonpedro.parking.repositories.VehicleRepository;

import jakarta.transaction.Transactional;

@Service
public class VehicleService {

	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private RegisterService registerService;
	
	@Transactional
	public Vehicle save(Vehicle vehicle, Long companyId) {
		validatePlate(vehicle.getPlate());
		vehicle = prepareToSave(vehicle, companyId);
		validateSpacesForParking(companyId, vehicle.getType());
		companyService.addVehicleInVacantSpace(vehicle.getCompany().getId(), vehicle);
		return vehicleRepository.save(vehicle);
	}

	@Transactional
	private Vehicle prepareToSave(Vehicle vehicle, Long companyId) {
		Company company = companyRepository.findById(companyId).get();
		vehicle.setCompany(company);
		vehicle.undefined();
		return vehicle;
	}

	public List<Vehicle> findAll() {
		return vehicleRepository.findAll();
	}
	
	public Vehicle findById(Long id) {
		return vehicleRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(id));
	}

	public Vehicle update(Vehicle vehicle, Long companyId, Long vehicleId) {
		
		Company company = companyRepository.findById(companyId).get();
		
		return vehicleRepository.findById(vehicleId)
				.map(vehicleUpdated -> {
					vehicleUpdated.setId(vehicleId);
					vehicleUpdated.setBrand(vehicle.getBrand());
					vehicleUpdated.setModel(vehicle.getModel());
					vehicleUpdated.setColor(vehicle.getColor());
					vehicleUpdated.setPlate(vehicle.getPlate());
					vehicleUpdated.setType(vehicle.getType());
					vehicleUpdated.setCompany(company);
					return vehicleRepository.save(vehicleUpdated);
				}).orElseThrow(() -> new NotFoundException(vehicleId));
	}
	
	@Transactional
	public void parkVehicle(Long vehicleId) {
		Vehicle vehicle = findById(vehicleId);
		
		validateVehicleStatus(vehicle.getStatus(), VehicleStatus.PARKED);
		
		vehicle.park();
		
		registerService.save(vehicle);
		vehicleRepository.save(vehicle);
	}

	@Transactional
	public void notParkVehicle(Long vehicleId) {
		Vehicle vehicle = findById(vehicleId);
		
		validateVehicleStatus(vehicle.getStatus(), VehicleStatus.NOT_PARKED);
		
		vehicle.notPark();
		
		registerService.save(vehicle);
		vehicleRepository.save(vehicle);
	}

	@Transactional
	public void delete(Long id) {
		removeVehicleInVacantSpace(id);
		
		vehicleRepository.delete(vehicleRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(id)));
	}
	
	private void removeVehicleInVacantSpace(Long id) {
		Vehicle vehicle = findById(id);
		Company company = companyService.findById(vehicle.getCompany().getId());
		companyService.removeVehicleInVacantSpace(company, vehicle);
	}

	public Summary getSummary() {
		Summary summary = new Summary();
		
		var list = registerService.finAll();
		var listDto = list.stream().map(x -> new RegisterDTO(x)).toList();
		
		summary.setRegisters(listDto);
		summary.setNumberOfRecords(list.size());
		summary.setInputQuantity(summary.count(list, EntranceAndExit.ENTRANCE));
		summary.setOutputQuantity(summary.count(list, EntranceAndExit.EXIT));
		
		return summary;
	}
	
	public Summary getSummaryByVehicleId(Long vehicleId) {
		Summary summary = new Summary();
		
		findById(vehicleId);
		
		var list = registerService.finAll().stream()
				.filter(register -> register.getVehicle().getId().equals(vehicleId))
				.collect(Collectors.toList());
		
		var listDto = list.stream().map(x -> new RegisterDTO(x)).toList();
		
		summary.setRegisters(listDto);
		summary.setNumberOfRecords(list.size());
		summary.setInputQuantity(summary.count(list, EntranceAndExit.ENTRANCE));
		summary.setOutputQuantity(summary.count(list, EntranceAndExit.EXIT));
		
		return summary;
	}
	
	private void validatePlate(String plate) {
		if(vehicleRepository.existsByPlate(plate)) {
			throw new ExistingPlateException(plate);
		}
	}
	
	private void validateSpacesForParking(Long companyId, TypeVehicle typeVehicle) {
		Company company = companyService.findById(companyId);
		if(typeVehicle.equals(TypeVehicle.CAR)) {
			if(company.getSpacesForCars() == 0) 
				throw new LimitOfSpacesException();
		} else if (typeVehicle.equals(TypeVehicle.MOTORBIKE)) {
			if(company.getSpacesForMotorbikes() == 0) 
				throw new LimitOfSpacesException();
		}
	}
	
	private void validateVehicleStatus(VehicleStatus statusNow, VehicleStatus status) {
		if(statusNow.equals(status))
			throw new ParkedException();
	}
}
