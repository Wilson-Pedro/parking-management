package com.wilsonpedro.parking.dtos;

import com.wilsonpedro.parking.models.Address;
import com.wilsonpedro.parking.models.Company;

public class CompanyDTO {

	private Long id;
	
	private String name;
	
	private String cnpj;
	
	private Address address;
	
	private String phone; 
	
	private Integer spacesForMotorbikes;
	
	private Integer spacesForCars;

	public CompanyDTO() {
	}
	
	public CompanyDTO(Company company) {
		id = company.getId();
		name = company.getName();
		cnpj = company.getCnpj();
		address = company.getAddress();
		phone = company.getPhone();
		spacesForMotorbikes = company.getSpacesForMotorbikes();
		spacesForCars = company.getSpacesForCars();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer getSpacesForMotorbikes() {
		return spacesForMotorbikes;
	}

	public void setSpacesForMotorbikes(Integer spacesForMotorbikes) {
		this.spacesForMotorbikes = spacesForMotorbikes;
	}

	public Integer getSpacesForCars() {
		return spacesForCars;
	}

	public void setSpacesForCars(Integer spacesForCars) {
		this.spacesForCars = spacesForCars;
	}
}
