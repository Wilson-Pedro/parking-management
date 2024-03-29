package com.wilsonpedro.parking.models;

import java.io.Serializable;

import com.wilsonpedro.parking.enums.EntranceAndExit;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "TB_REGISTER")
public class Register implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;
	
	private EntranceAndExit entranceAndExit;
	
	private String localDateTime;

	public Register() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public EntranceAndExit getEntranceAndExit() {
		return entranceAndExit;
	}

	public void setEntranceAndExit(EntranceAndExit entranceAndExit) {
		this.entranceAndExit = entranceAndExit;
	}

	public String getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(String localDateTime) {
		this.localDateTime = localDateTime;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
}
