package com.mycompany.app.transaction.gateway.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class IdCodeDTO {
	private String code;

	@JsonCreator
	public IdCodeDTO(@JsonProperty("code") String code) {
		this.code = code;
	}

	public IdCodeDTO() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
