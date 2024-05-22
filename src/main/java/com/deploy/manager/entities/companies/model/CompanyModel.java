package com.deploy.manager.entities.companies.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@ToString
@Getter
@Setter
@Table(name = "companies")
public class CompanyModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(name = "name_company")
	private String nameCompany;

	@NotBlank
	private String description;
}


