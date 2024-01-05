package com.wilsonpedro.parking.dtos;

public class ParkDTO {

	private Long companyId;

	public ParkDTO() {
	}

	public ParkDTO(Long companyId) {
		this.companyId = companyId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
}
