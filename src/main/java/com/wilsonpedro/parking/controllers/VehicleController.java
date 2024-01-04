package com.wilsonpedro.parking.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wilsonpedro.parking.dtos.ParkDTO;
import com.wilsonpedro.parking.models.Vehicle;
import com.wilsonpedro.parking.services.CompanyService;
import com.wilsonpedro.parking.services.VehicleService;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;
	
//	@Autowired
//	private CompanyService companyService;
	
	@PostMapping("/")
	public ResponseEntity save(@RequestBody Vehicle vehicle) {
		Vehicle vehicleSaved = vehicleService.save(vehicle);
		return ResponseEntity.status(HttpStatus.CREATED).body(vehicleSaved);
	}
	
	@GetMapping
	public ResponseEntity findAll() {
		List<Vehicle> list = vehicleService.findAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity findById(@PathVariable Long id) {
		Vehicle vehicle = vehicleService.findById(id);
		return ResponseEntity.ok(vehicle);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity update(@RequestBody Vehicle vehicle, @PathVariable Long id) {
		Vehicle vehicleUpdated = vehicleService.update(vehicle, id);
		return ResponseEntity.ok(vehicleUpdated);
	}
	
//	@PostMapping("/vehicles/{id}")
//	public ResponseEntity park(@RequestBody ParkDTO parkDTO, @PathVariable Long vehicleId) {
//		companyService.parkVehicle(parkDTO.getEmpresaId(), vehicleId);
//		return ResponseEntity.noContent().build();
//	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		vehicleService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
