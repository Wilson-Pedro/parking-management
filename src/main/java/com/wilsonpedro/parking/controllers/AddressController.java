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

import com.wilsonpedro.parking.models.Address;
import com.wilsonpedro.parking.services.AddressService;

@RestController
@RequestMapping("/addreses")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@PostMapping("/")
	public ResponseEntity save(@RequestBody Address address) {
		Address addressSaved = addressService.save(address);
		return ResponseEntity.status(HttpStatus.CREATED).body(addressSaved);
	}
	
	@GetMapping
	public ResponseEntity findAll() {
		List<Address> list = addressService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity findAll(@PathVariable Long id) {
		Address address = addressService.findById(id);
		return ResponseEntity.ok().body(address);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity update(@RequestBody Address address, @PathVariable Long id) {
		Address addressUpdated = addressService.update(address, id);
		return ResponseEntity.ok().body(addressUpdated);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		addressService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
