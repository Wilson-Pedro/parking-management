package com.wilsonpedro.parking.dtos;

import java.io.Serializable;

import com.wilsonpedro.parking.models.Register;

public class RegisterDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long vehicleId;
	
	private String entranceAndExit;
	
	private String localDateTime;
	
	public RegisterDTO() {
	}

	public RegisterDTO(Register register) {
		vehicleId = register.getVehicle().getId();
		entranceAndExit = register.getEntranceAndExit().getDescription();
		localDateTime = register.getLocalDateTime();
	}

	public Long getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Long vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getEntranceAndExit() {
		return entranceAndExit;
	}

	public void setEntranceAndExit(String entranceAndExit) {
		this.entranceAndExit = entranceAndExit;
	}

	public String getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(String localDateTime) {
		this.localDateTime = localDateTime;
	}
}
