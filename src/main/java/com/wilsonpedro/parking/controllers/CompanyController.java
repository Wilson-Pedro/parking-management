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

import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.services.CompanyService;

@RestController
@RequestMapping("/companies")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@PostMapping("/")
	public ResponseEntity save(@RequestBody Company company) {
		Company companySaved = companyService.save(company);
		return ResponseEntity.status(HttpStatus.CREATED).body(companySaved);
	}
	
	@GetMapping
	public ResponseEntity findAll() {
		List<Company> list = companyService.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity findAll(@PathVariable Long id) {
		Company company = companyService.findById(id);
		return ResponseEntity.ok().body(company);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity update(@RequestBody Company company, @PathVariable Long id) {
		Company companyUpdated = companyService.update(company, id);
		return ResponseEntity.ok().body(companyUpdated);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		companyService.delete(id);
		return ResponseEntity.noContent().build();
	}

}