package com.wilsonpedro.parking.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.wilsonpedro.parking.enums.EntranceAndExit;
import com.wilsonpedro.parking.models.Register;

public class Summary implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer numberOfRecords;

	private Integer inputQuantity;
	
	private Integer outputQuantity;
	
	private List<RegisterDTO> registers = new ArrayList<>();
	
	public Summary() {
	}

	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	public Integer getInputQuantity() {
		return inputQuantity;
	}

	public void setInputQuantity(Integer inputQuantity) {
		this.inputQuantity = inputQuantity;
	}

	public Integer getOutputQuantity() {
		return outputQuantity;
	}

	public void setOutputQuantity(Integer outputQuantity) {
		this.outputQuantity = outputQuantity;
	}	
	
	public List<RegisterDTO> getRegisters() {
		return registers;
	}

	public void setRegisters(List<RegisterDTO> registers) {
		this.registers = registers;
	}

	public Integer count(List<Register> registers, EntranceAndExit entranceAndExit) {
		return registers.stream()
				.filter(register -> register.getEntranceAndExit().equals(entranceAndExit))
				.collect(Collectors.toList()).size();
	}
}
