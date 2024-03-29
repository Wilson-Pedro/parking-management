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

import com.wilsonpedro.parking.dtos.Summary;
import com.wilsonpedro.parking.dtos.VehicleDTO;
import com.wilsonpedro.parking.models.Vehicle;
import com.wilsonpedro.parking.services.VehicleService;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

	@Autowired
	private VehicleService vehicleService;
	
	@PostMapping("/")
	public ResponseEntity save(@RequestBody VehicleDTO vehicleDTO) {
		var vehicleSaved = vehicleService.save(new Vehicle(vehicleDTO), vehicleDTO.getCompanyId());
		VehicleDTO dtoSaved = new VehicleDTO(vehicleSaved);
		return ResponseEntity.status(HttpStatus.CREATED).body(dtoSaved);
	}
	
	@GetMapping
	public ResponseEntity findAll() {
		List<Vehicle> list = vehicleService.findAll();
		List<VehicleDTO> dtos = list.stream().map(x -> new VehicleDTO(x)).toList();
		return ResponseEntity.ok(dtos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity findById(@PathVariable Long id) {
		Vehicle vehicle = vehicleService.findById(id);
		VehicleDTO dtoFinded = new VehicleDTO(vehicle);
		return ResponseEntity.ok(dtoFinded);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity update(@RequestBody VehicleDTO vehicleDTO, @PathVariable Long id) {
		Vehicle vehicleUpdated = vehicleService.update(
				new Vehicle(vehicleDTO), vehicleDTO.getCompanyId(), id);
		VehicleDTO dtoUpdated  = new VehicleDTO(vehicleUpdated);
		return ResponseEntity.ok(dtoUpdated);
	}
	
	@PutMapping("/{id}/park")
	public ResponseEntity park(@PathVariable("id") Long vehicleId) {
		vehicleService.parkVehicle(vehicleId);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}/notPark")
	public ResponseEntity notPpark(@PathVariable("id") Long vehicleId) {
		vehicleService.notParkVehicle(vehicleId);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/summary")
	public ResponseEntity summary() {
		Summary summary = vehicleService.getSummary();
		return ResponseEntity.ok(summary);
	}
	
	@GetMapping("/{id}/summary")
	public ResponseEntity summaryByVehicle(@PathVariable Long id) {
		Summary summary = vehicleService.getSummaryByVehicleId(id);
		return ResponseEntity.ok(summary);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		vehicleService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
