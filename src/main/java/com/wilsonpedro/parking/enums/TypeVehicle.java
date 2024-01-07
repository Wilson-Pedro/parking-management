package com.wilsonpedro.parking.enums;

import java.util.stream.Stream;

public enum TypeVehicle {

	CAR(1, "Car"),
	MOTORBIKE(2, "Motobike");
	
	private Integer cod;
	
	private String description;
	
	private TypeVehicle(Integer cod, String description) {
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
	
	public static TypeVehicle toEnum(String description) {
		return Stream.of(TypeVehicle.values())
				.filter(tipo -> tipo.getDescription().equals(description))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException
						("Tipo de veículo inválido: " + description));
	}
}
