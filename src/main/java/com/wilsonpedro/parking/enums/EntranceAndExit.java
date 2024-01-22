package com.wilsonpedro.parking.enums;

import java.util.stream.Stream;

public enum EntranceAndExit {

	ENTRANCE(1, "Entrance"),
	EXIT(2, "Exit"),
	UNDEFINED(3, "Undefined");
	
	private Integer cod;
	
	private String description;
	
	private EntranceAndExit(Integer cod, String description) {
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
	
	public static EntranceAndExit toEnum(String description) {
		return Stream.of(EntranceAndExit.values())
				.filter(x -> x.getDescription().equals(description))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("EntranceAndExit invalid!"));
	}
}
