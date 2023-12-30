package com.wilsonpedro.parking.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String cnpj;
	
	@OneToOne(mappedBy = "company")
	private Address address;
	
	private String phone; 
	
	private Integer spacesForMotocycles;
	
	private Integer spacesForCars;
	
	public Company() {
	}

	public Company(Long id, String name, String cnpj, Address address, String phone, Integer spacesForMotocycles,
			Integer spacesForCars) {
		this.id = id;
		this.name = name;
		this.cnpj = cnpj;
		this.address = address;
		this.phone = phone;
		this.spacesForMotocycles = spacesForMotocycles;
		this.spacesForCars = spacesForCars;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getSpacesForMotocycles() {
		return spacesForMotocycles;
	}

	public void setSpacesForMotocycles(Integer spacesForMotocycles) {
		this.spacesForMotocycles = spacesForMotocycles;
	}

	public Integer getSpacesForCars() {
		return spacesForCars;
	}

	public void setSpacesForCars(Integer spacesForCars) {
		this.spacesForCars = spacesForCars;
	}
}
