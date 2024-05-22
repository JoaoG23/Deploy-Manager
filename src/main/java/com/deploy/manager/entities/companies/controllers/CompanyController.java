package com.deploy.manager.entities.companies.controllers;

import com.deploy.manager.entities.companies.dtos.CompanyDTO;
import com.deploy.manager.entities.companies.model.CompanyModel;
import com.deploy.manager.entities.companies.services.CompanyServices;

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
@RequestMapping("/companies")
public class CompanyController {
	@Autowired
	private CompanyServices companyServices;

	@PostMapping
	public ResponseEntity<CompanyModel> createOne(@RequestBody @Valid CompanyDTO companyDTO) {

		CompanyModel response = companyServices.create(companyDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CompanyModel> updateById(@PathVariable(value = "id") Long id, @RequestBody @Valid CompanyDTO companyDTO) {

		var company = companyServices.updateById(id, companyDTO);
		return ResponseEntity.ok().body(company);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable(value = "id") Long id) {
		var msg = companyServices.deleteById(id);
		return ResponseEntity.ok().body(msg);
	}

	@GetMapping
	public ResponseEntity<List<CompanyDTO>> findAll() {
		var companies = companyServices.findAll();
		return ResponseEntity.ok().body(companies);
	}

	@GetMapping("/{id}")
	public ResponseEntity<CompanyDTO> findOneById(@PathVariable(value = "id") Long id) {
		var company = companyServices.findById(id);
		return (ResponseEntity<CompanyDTO>) ResponseEntity.ok().body(company);
	}

	@GetMapping("page")
	public ResponseEntity<Page<CompanyDTO>> findAllByPage(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		var page = companyServices.findAllByPage(pageable);
		return ResponseEntity.ok().body(page);
	}
}
