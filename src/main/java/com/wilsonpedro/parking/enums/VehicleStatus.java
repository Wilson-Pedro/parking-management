package com.wilsonpedro.parking.enums;

import java.util.stream.Stream;

public enum VehicleStatus {

	PARKED(1, "Parked"),
	NOT_PARKED(2, "Not Parked"),
	UNDEFINED(3, "Undefined");
	
	private Integer cod;
	
	private String description;
	
	private VehicleStatus(Integer cod, String description) {
		this.cod = cod;
		this.description = description;
	}

	public Integer getCod() {
		return cod;
	}

	public void setCod(Integer cod) {
		this.cod = cod;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static VehicleStatus toEnum(String description) {
		return Stream.of(VehicleStatus.values())
				.filter(status -> status.getDescription().equals(description))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException
						("Status inválido: " + description));
	}
}
