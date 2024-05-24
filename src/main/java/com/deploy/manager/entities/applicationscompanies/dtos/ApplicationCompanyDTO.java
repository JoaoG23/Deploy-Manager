package com.deploy.manager.entities.applicationscompanies.dtos;

import com.deploy.manager.entities.applicationscompanies.enums.StatusApplication;
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

	@NotNull
	public Long idCompanies;

	@NotNull
	public Long idApplications;

	public String notesFrontend;

	public String notesBackend;

	@NotNull
	@Enumerated(EnumType.STRING)
	public StatusApplication status;
}
