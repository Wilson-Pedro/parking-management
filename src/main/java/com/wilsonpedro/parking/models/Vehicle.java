package com.wilsonpedro.parking.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wilsonpedro.parking.dtos.VehicleDTO;
import com.wilsonpedro.parking.enums.TypeVehicle;
import com.wilsonpedro.parking.enums.VehicleStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_VEHICLE")
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String brand;
	private String model;
	private String color;
	private String plate;
	private TypeVehicle type;
	private VehicleStatus status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id")
	private Company company;
	
	@JsonIgnore
	@OneToMany(mappedBy="vehicle", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Register> registers = new ArrayList<>();
	
	public Vehicle() {
	}

	public Vehicle(Long id, String brand, String model, String color, String plate, TypeVehicle type, VehicleStatus status) {
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.plate = plate;
		this.type = type;
		this.status = status;
	}
	
	public Vehicle(VehicleDTO vehicleDTO) {
		id = vehicleDTO.getId();
		brand = vehicleDTO.getBrand();
		model = vehicleDTO.getModel();
		color = vehicleDTO.getColor();
		plate = vehicleDTO.getPlate();
		type = TypeVehicle.toEnum(vehicleDTO.getType());
		status = VehicleStatus.toEnum(vehicleDTO.getStatus());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public TypeVehicle getType() {
		return type;
	}

	public void setType(TypeVehicle type) {
		this.type = type;
	}

	public VehicleStatus getStatus() {
		return status;
	}

	public void setStatus(VehicleStatus status) {
		this.status = status;
	}
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Register> getRegisters() {
		return registers;
	}

	public void park() {
		setStatus(VehicleStatus.PARKED);
	}
	
	public void notPark() {
		setStatus(VehicleStatus.NOT_PARKED);
	}
	
	public void undefined() {
		setStatus(VehicleStatus.UNDEFINED);
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
		Vehicle other = (Vehicle) obj;
		return Objects.equals(id, other.id);
	}
}
