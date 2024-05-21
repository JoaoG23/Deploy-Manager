package com.deploy.manager.entities.applications.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@ToString
@Getter
@Setter
@Table(name = "applications")
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(name = "name_application")
	private String nameApplication;

	@NotBlank
	private String description;
}


