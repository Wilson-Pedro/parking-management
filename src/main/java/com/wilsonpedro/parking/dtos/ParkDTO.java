package com.wilsonpedro.parking.dtos;

public class ParkDTO {

	private Long empresaId;

	public ParkDTO(Long empresaId) {
	}

	public Long getEmpresaId() {
		return empresaId;
	}

	public void setEmpresaId(Long empresaId) {
		this.empresaId = empresaId;
	}
}
