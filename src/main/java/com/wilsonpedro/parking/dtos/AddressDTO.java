package com.wilsonpedro.parking.dtos;

import com.wilsonpedro.parking.models.Address;

public class AddressDTO {

	private Long id;
	
	private String cep;
	
	private String street;
	
	private String neighborhood;
	
	private String city;

	public AddressDTO() {
	}

	public AddressDTO(String cep, String street, String neighborhood, String city) {
		this.cep = cep;
		this.street = street;
		this.neighborhood = neighborhood;
		this.city = city;
	}
	
	public AddressDTO(Address address) {
		id = address.getId();
		cep = address.getCep();
		street = address.getStreet();
		neighborhood = address.getNeighborhood();
		city = address.getCity();
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
}
