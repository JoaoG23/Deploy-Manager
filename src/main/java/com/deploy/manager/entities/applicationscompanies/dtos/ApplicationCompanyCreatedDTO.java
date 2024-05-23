package com.deploy.manager.entities.applicationscompanies.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ApplicationCompanyCreatedDTO {

	@NotEmpty
	private Long idCompanies;

	@NotEmpty
	private Long idApplications;

	private String notesFrontend;

	private String notesBackend;

	@NotEmpty
	private String status;
}
