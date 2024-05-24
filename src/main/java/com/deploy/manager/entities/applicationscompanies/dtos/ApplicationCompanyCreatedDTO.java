package com.deploy.manager.entities.applicationscompanies.dtos;

import com.deploy.manager.entities.applicationscompanies.enums.StatusApplication;
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
	private Enum<StatusApplication> status;
}
