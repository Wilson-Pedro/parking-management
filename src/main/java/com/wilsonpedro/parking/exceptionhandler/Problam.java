package com.wilsonpedro.parking.exceptionhandler;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class Problam implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String title;
	
	private Integer code;
	
	private OffsetDateTime dateTime;
	
	public Problam() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public OffsetDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(OffsetDateTime dateTime) {
		this.dateTime = dateTime;
	}
}
