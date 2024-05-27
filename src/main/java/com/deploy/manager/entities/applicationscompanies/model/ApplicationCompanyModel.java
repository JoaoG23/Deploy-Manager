package com.deploy.manager.entities.applicationscompanies.model;

import com.deploy.manager.entities.applications.model.ApplicationModel;
import com.deploy.manager.entities.applicationscompanies.enums.StatusApplication;
import com.deploy.manager.entities.companies.model.CompanyModel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

@Entity
@ToString
@Getter
@Setter
@Table(name = "companies_applications")
public class ApplicationCompanyModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_companies", nullable = false)
	private CompanyModel company;

	@ManyToOne
	@JoinColumn(name = "id_applications", nullable = false)
	private ApplicationModel application;

	@Column(name = "notes_frontend")
	private String notesFrontend;

	@Column(name = "notes_backend")
	private String notesBackend;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private StatusApplication status;


}


