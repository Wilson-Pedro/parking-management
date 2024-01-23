package com.wilsonpedro.parking.dtos;

import java.io.Serializable;

import com.wilsonpedro.parking.models.Company;

public class CompanyMinDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String name;
	
	private String cnpj;
	
	private Long addressId;
	
	private String phone; 
	
	private Integer spacesForMotorbikes;
	
	private Integer spacesForCars;

	public CompanyMinDTO(Company company) {
		id = company.getId();
		name = company.getName();
		cnpj = company.getCnpj();
		addressId = company.getAddress() == null ? null : company.getAddress().getId();
		phone = company.getPhone();
		spacesForMotorbikes = company.getSpacesForMotorbikes();
		spacesForCars = company.getSpacesForCars();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getCnpj() {
		return cnpj;
	}

	public Long getAddressId() {
		return addressId;
	}

	public String getPhone() {
		return phone;
	}

	public Integer getSpacesForMotorbikes() {
		return spacesForMotorbikes;
	}

	public Integer getSpacesForCars() {
		return spacesForCars;
	}
}
