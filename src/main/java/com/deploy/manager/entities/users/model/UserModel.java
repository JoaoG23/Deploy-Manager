package com.deploy.manager.entities.users.model;


import com.deploy.manager.entities.users.dtos.UserDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "users")
@ToString
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
}
