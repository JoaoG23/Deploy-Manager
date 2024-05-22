package com.deploy.manager.entities.companies.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CompanyCreatedDTO {

	@NotEmpty
	String name_company;

	@NotEmpty
	String description;
}
