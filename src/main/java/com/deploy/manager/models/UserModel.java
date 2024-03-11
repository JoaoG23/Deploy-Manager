package com.deploy.manager.models;

import com.deploy.manager.dtos.users.CreateUserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "users")
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	private String full_name;
	public UserModel(CreateUserDTO userDTO) {}
}
