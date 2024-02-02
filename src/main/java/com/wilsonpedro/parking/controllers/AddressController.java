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

import com.wilsonpedro.parking.dtos.AddressDTO;
import com.wilsonpedro.parking.models.Address;
import com.wilsonpedro.parking.services.AddressService;

@RestController
@RequestMapping("/addreses")
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@PostMapping("/")
	public ResponseEntity save(@RequestBody AddressDTO addressDTO) {
		Address addressSaved = addressService.save(new Address(addressDTO), addressDTO.getCompanyId());
		AddressDTO dtoSaved = new AddressDTO(addressSaved);
		return ResponseEntity.status(HttpStatus.CREATED).body(dtoSaved);
	}
	
	@GetMapping
	public ResponseEntity findAll() {
		List<Address> list = addressService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity findAll(@PathVariable Long id) {
		Address address = addressService.findById(id);
		AddressDTO dtoFinded = new AddressDTO(address);
		return ResponseEntity.ok().body(dtoFinded);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity update(@RequestBody AddressDTO addressDTO, @PathVariable Long id) {
		Address addressUpdated = addressService.update(new Address(addressDTO), id);
		AddressDTO dtoUpdated = new AddressDTO(addressUpdated);
		return ResponseEntity.ok().body(dtoUpdated);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		addressService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
