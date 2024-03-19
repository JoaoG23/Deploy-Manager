package com.deploy.manager.entities.users.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class UserDTO {

	private Long id;

	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	@NotEmpty
	private String full_name;
}
