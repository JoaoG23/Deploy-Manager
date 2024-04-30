package com.deploy.manager.entities.applications.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApplicationDTO {

	public Long id;
	@NotEmpty
	public String nameApplication;
	@NotEmpty
	public String description;

}
