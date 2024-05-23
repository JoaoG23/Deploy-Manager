package com.deploy.manager.entities.applicationscompanies.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApplicationCompanyDTO {

	public Long id;

	@NotEmpty
	public Long idCompanies;

	@NotEmpty
	public Long idApplications;

	public String notesFrontend;

	public String notesBackend;

	@NotEmpty
	public String status;
}
