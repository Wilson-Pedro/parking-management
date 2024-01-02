package com.wilsonpedro.parking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wilsonpedro.parking.models.Vehicle;
import com.wilsonpedro.parking.repositories.VehicleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class VehicleService {

	@Autowired
	private VehicleRepository vehicleRepository;
	
	public Vehicle save(Vehicle vehicle) {
		return vehicleRepository.save(vehicle);
	}
	 
	public List<Vehicle> findAll() {
		return vehicleRepository.findAll();
	}
	
	public Vehicle findById(Long id) {
		return vehicleRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
	}

	public Vehicle update(Vehicle vehicle, Long id) {
		return vehicleRepository.findById(id)
				.map(vehicleUpdated -> {
					vehicleUpdated.setId(id);
					vehicleUpdated.setBrand(vehicle.getBrand());
					vehicleUpdated.setModel(vehicle.getModel());
					vehicleUpdated.setColor(vehicle.getColor());
					vehicleUpdated.setPlate(vehicle.getPlate());
					vehicleUpdated.setType(vehicle.getType());
					return vehicleRepository.save(vehicleUpdated);
				}).orElseThrow(() -> new EntityNotFoundException());
	}

	public void delete(Long id) {
		vehicleRepository.delete(vehicleRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException()));
	}
}
