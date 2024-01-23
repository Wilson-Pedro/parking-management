package com.wilsonpedro.parking.dtos;

import java.io.Serializable;

import com.wilsonpedro.parking.models.Address;

public class AddressDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String cep;
	
	private String street;
	
	private String neighborhood;
	
	private String city;
	
	private Long companyId;

	public AddressDTO() {
	}
	
	public AddressDTO(String cep, String street, String neighborhood, String city, Long companyId) {
		this.cep = cep;
		this.street = street;
		this.neighborhood = neighborhood;
		this.city = city;
		this.companyId = companyId;
	}

	public AddressDTO(Address address) {
		id = address.getId();
		cep = address.getCep();
		street = address.getStreet();
		neighborhood = address.getNeighborhood();
		city = address.getCity();
		companyId = address.getCompany().getId();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}
