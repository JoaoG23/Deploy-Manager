package com.deploy.manager.entities.applications.controllers;

import com.deploy.manager.entities.applications.dtos.ApplicationDTO;
import com.deploy.manager.entities.applications.model.ApplicationModel;
import com.deploy.manager.entities.applications.services.ApplicationServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apps")
public class ApplicationsController {
	@Autowired
	private ApplicationServices applicationServices;

	@PostMapping
	public ResponseEntity<ApplicationModel> createOne(@RequestBody @Valid ApplicationDTO applicationDTO) {

		ApplicationModel response = applicationServices.create(applicationDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}