package com.wilsonpedro.parking.models;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wilsonpedro.parking.dtos.AddressDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_ADDRESS")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String cep;
	
	private String street;
	
	private String neighborhood;
	
	private String city;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "company_id")
	private Company company;
	
	public Address() {
	}
	
	public Address(Long id, String cep, String street, String neighborhood, String city) {
		this.id = id;
		this.cep = cep;
		this.street = street;
		this.neighborhood = neighborhood;
		this.city = city;
	}

	public Address(AddressDTO addressDTO) {
		cep = addressDTO.getCep();
		street = addressDTO.getStreet();
		neighborhood = addressDTO.getNeighborhood();
		city = addressDTO.getCity();
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

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return Objects.equals(id, other.id);
	}
}
