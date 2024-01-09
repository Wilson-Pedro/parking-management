package com.wilsonpedro.parking.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wilsonpedro.parking.dtos.CompanyInputDTO;
import com.wilsonpedro.parking.dtos.CompanyMinDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	
	private Integer spacesForMotorbikes;
	
	private Integer spacesForCars;
	
	@JsonIgnore
	@OneToMany(mappedBy = "company", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Vehicle> vehicles = new ArrayList<>();
	
	public Company() {
	}

	public Company(Long id, String name, String cnpj, Address address, String phone, Integer spacesForMotorbikes,
			Integer spacesForCars) {
		this.id = id;
		this.name = name;
		this.cnpj = cnpj;
		this.address = address;
		this.phone = phone;
		this.spacesForMotorbikes = spacesForMotorbikes;
		this.spacesForCars = spacesForCars;
	}
	
	public Company(CompanyMinDTO companyMinDTO) {
		name = companyMinDTO.getName();
		cnpj = companyMinDTO.getCnpj();
		phone = companyMinDTO.getPhone();
		spacesForMotorbikes = companyMinDTO.getSpacesForMotorbikes();
		spacesForCars = companyMinDTO.getSpacesForCars();
	}
	
	public Company(CompanyInputDTO companyInputDTO) {
		name = companyInputDTO.getName();
		cnpj = companyInputDTO.getCnpj();
		phone = companyInputDTO.getPhone();
		spacesForMotorbikes = companyInputDTO.getSpacesForMotorbikes();
		spacesForCars = companyInputDTO.getSpacesForCars();
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

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void decrementOneInTheCarSpace() {
		spacesForCars -= 1;
		setSpacesForCars(spacesForCars);
	}
	
	public void decrementOneInTheMotorbikesSpace() {
		spacesForMotorbikes -= 1;
		setSpacesForMotorbikes(spacesForMotorbikes);
	}

	public void increaseOneInTheCarSpace() {
		spacesForCars += 1;
		setSpacesForCars(spacesForCars);
	}
	
	public void increaseOneInTheMotorbikesSpace() {
		spacesForMotorbikes += 1;
		setSpacesForMotorbikes(spacesForMotorbikes);
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
		Company other = (Company) obj;
		return Objects.equals(id, other.id);
	}
}
