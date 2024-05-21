package com.deploy.manager.entities.applications.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ApplicationCreatedDTO {

	@NotEmpty
	String name_application;

	@NotEmpty
	String description;
}
