package com.deploy.manager.entities.companies.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CompanyDTO {

	public Long id;
	@NotEmpty
	public String nameCompany;
	@NotEmpty
	public String description;

}
