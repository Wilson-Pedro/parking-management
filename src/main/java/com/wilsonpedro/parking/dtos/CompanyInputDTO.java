package com.wilsonpedro.parking.dtos;

import java.io.Serializable;

import com.wilsonpedro.parking.models.Company;

public class CompanyInputDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private String cnpj;
	
	private String phone; 
	
	private Integer spacesForMotorbikes;
	
	private Integer spacesForCars;

	public CompanyInputDTO() {
	}
	
	public CompanyInputDTO(String name, String cnpj, String phone, Integer spacesForMotorbikes, Integer spacesForCars) {
		this.name = name;
		this.cnpj = cnpj;
		this.phone = phone;
		this.spacesForMotorbikes = spacesForMotorbikes;
		this.spacesForCars = spacesForCars;
	}

	public CompanyInputDTO(Company company) {
		name = company.getName();
		cnpj = company.getCnpj();
		phone = company.getPhone();
		spacesForMotorbikes = company.getSpacesForMotorbikes();
		spacesForCars = company.getSpacesForCars();
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
