package com.deploy.manager.entities.applicationscompanies.dtos;

import com.deploy.manager.entities.applications.model.ApplicationModel;
import com.deploy.manager.entities.applicationscompanies.enums.StatusApplication;
import com.deploy.manager.entities.companies.dtos.CompanyDTO;
import com.deploy.manager.entities.companies.model.CompanyModel;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApplicationCompanyDTO {

	public Long id;

	public CompanyModel company;
	public ApplicationModel application;

	@NotNull
	public Long companyId;

	@NotNull
	public Long applicationId;

	public String notesFrontend;

	public String notesBackend;

	@NotNull
	@Enumerated(EnumType.STRING)
	public StatusApplication status;
}
