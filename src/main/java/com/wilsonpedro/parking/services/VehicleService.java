package com.wilsonpedro.parking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wilsonpedro.parking.dtos.VehicleDTO;
import com.wilsonpedro.parking.enums.TypeVehicle;
import com.wilsonpedro.parking.models.Vehicle;
import com.wilsonpedro.parking.repositories.VehicleRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class VehicleService {

	@Autowired
	private VehicleRepository vehicleRepository;
	
	public Vehicle save(VehicleDTO vehicleDTO) {
		return vehicleRepository.save(new Vehicle(vehicleDTO));
	}
	 
	public List<Vehicle> findAll() {
		return vehicleRepository.findAll();
	}
	
	public Vehicle findById(Long id) {
		return vehicleRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException());
	}

	public Vehicle update(VehicleDTO vehicleDTO, Long id) {
		return vehicleRepository.findById(id)
				.map(vehicleUpdated -> {
					vehicleUpdated.setId(id);
					vehicleUpdated.setBrand(vehicleDTO.getBrand());
					vehicleUpdated.setModel(vehicleDTO.getModel());
					vehicleUpdated.setColor(vehicleDTO.getColor());
					vehicleUpdated.setPlate(vehicleDTO.getPlate());
					vehicleUpdated.setType(TypeVehicle.toEnum(vehicleDTO.getType()));
					return vehicleRepository.save(vehicleUpdated);
				}).orElseThrow(() -> new EntityNotFoundException());
	}

	public void delete(Long id) {
		vehicleRepository.delete(vehicleRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException()));
	}
}
