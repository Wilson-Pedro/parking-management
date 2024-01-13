package com.wilsonpedro.parking.dtos;

import com.wilsonpedro.parking.models.Vehicle;

public class VehicleDTO {

	private Long id;
	private String brand;
	private String model;
	private String color;
	private String plate;
	private String type;
	private String status;
	private Long companyId;
	
	public VehicleDTO() {
	}

	public VehicleDTO(String brand, String model, String color, String plate, String type, String status, Long companyId) {
		this.brand = brand;
		this.model = model;
		this.color = color;
		this.plate = plate;
		this.type = type;
		this.status = status;
		this.companyId = companyId;
	}
	
	public VehicleDTO(Vehicle vehicle) {
		id = vehicle.getId();
		brand = vehicle.getBrand();
		model = vehicle.getModel();
		color = vehicle.getColor();
		plate = vehicle.getPlate();
		type = vehicle.getType().getDescription();
		status = vehicle.getStatus().getDescription();
		companyId = vehicle.getCompany().getId();
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}
