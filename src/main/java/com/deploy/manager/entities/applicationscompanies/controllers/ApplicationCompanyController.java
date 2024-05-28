package com.deploy.manager.entities.applicationscompanies.controllers;

import com.deploy.manager.entities.applicationscompanies.dtos.ApplicationCompanyDTO;
import com.deploy.manager.entities.applicationscompanies.model.ApplicationCompanyModel;
import com.deploy.manager.entities.applicationscompanies.services.ApplicationCompanyServices;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/application-companies")
public class ApplicationCompanyController {
	@Autowired
	private ApplicationCompanyServices applicationCompanyServices;

	@PostMapping
	public ResponseEntity<ApplicationCompanyModel> createOne(@RequestBody @Valid ApplicationCompanyDTO applicationCompanyDTO) {
		ApplicationCompanyModel response = applicationCompanyServices.create(applicationCompanyDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApplicationCompanyModel> updateById(@PathVariable(value = "id") Long id, @RequestBody @Valid ApplicationCompanyDTO applicationCompanyDTO) {
		ApplicationCompanyModel applicationCompany = applicationCompanyServices.updateById(id, applicationCompanyDTO);
		return ResponseEntity.ok().body(applicationCompany);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable(value = "id") Long id) {
		String msg = applicationCompanyServices.deleteById(id);
		return ResponseEntity.ok().body(msg);
	}

	@GetMapping
	public ResponseEntity<List<ApplicationCompanyDTO>> findAll() {
		List<ApplicationCompanyDTO> applicationCompanies = applicationCompanyServices.findAll();
		return ResponseEntity.ok().body(applicationCompanies);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApplicationCompanyDTO> findOneById(@PathVariable(value = "id") Long id) {
		ApplicationCompanyDTO applicationCompany = applicationCompanyServices.findById(id);
		return ResponseEntity.ok().body(applicationCompany);
	}

	@GetMapping("/page")
	public ResponseEntity<Page<ApplicationCompanyDTO>> findAllByPage(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<ApplicationCompanyDTO> page = applicationCompanyServices.findAllByPage(pageable);
		return ResponseEntity.ok().body(page);
	}
}
