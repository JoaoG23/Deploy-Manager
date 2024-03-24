package com.deploy.manager.entities.users.authentication.dtos;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserAutheticationDto {
	private String login;
	private String senha;
}
