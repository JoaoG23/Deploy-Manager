package com.deploy.manager.entities.users.dtos;

import com.deploy.manager.entities.users.model.UserModel;

public record FindUserDTO(Long id, String username, String password, String full_name) {

	public FindUserDTO(UserModel userModel) {
		this(
				userModel.getId(),
				userModel.getUsername(),
				userModel.getPassword(),
				userModel.getFull_name()
		);
	}
}
