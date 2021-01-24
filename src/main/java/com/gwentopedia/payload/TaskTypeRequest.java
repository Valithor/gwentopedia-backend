package com.gwentopedia.payload;

import javax.validation.constraints.NotBlank;

public class TaskTypeRequest {
	@NotBlank
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
