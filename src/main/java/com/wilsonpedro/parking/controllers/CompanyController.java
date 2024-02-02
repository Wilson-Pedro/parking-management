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

import com.wilsonpedro.parking.dtos.CompanyDTO;
import com.wilsonpedro.parking.dtos.CompanyInputDTO;
import com.wilsonpedro.parking.dtos.CompanyMinDTO;
import com.wilsonpedro.parking.models.Company;
import com.wilsonpedro.parking.services.CompanyService;

@RestController
@RequestMapping("/companies")
public class CompanyController {
	
	@Autowired
	private CompanyService companyService;
	
	@PostMapping("/")
	public ResponseEntity save(@RequestBody CompanyInputDTO companyInputMinDTO) {
		Company companySaved = companyService.save(new Company(companyInputMinDTO));
		CompanyDTO companyDTO = new CompanyDTO(companySaved);
		return ResponseEntity.status(HttpStatus.CREATED).body(companyDTO);
	}
	
	@GetMapping
	public ResponseEntity findAll() {
		List<Company> list = companyService.findAll();
		List<CompanyMinDTO> dtos = list.stream().map(x -> new CompanyMinDTO(x)).toList();
		return ResponseEntity.ok().body(dtos);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity findAll(@PathVariable Long id) {
		Company company = companyService.findById(id);
		CompanyDTO dtoFinded = new CompanyDTO(company);
		return ResponseEntity.ok().body(dtoFinded);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity update(@RequestBody CompanyInputDTO companyInput, @PathVariable Long id) {
		Company companyUpdated = companyService.update(new Company(companyInput), id);
		CompanyDTO dtoUpdated= new CompanyDTO(companyUpdated);
		return ResponseEntity.ok().body(dtoUpdated);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable Long id) {
		companyService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
