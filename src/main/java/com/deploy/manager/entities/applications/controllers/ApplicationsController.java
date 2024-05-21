package com.deploy.manager.entities.applications.controllers;

import com.deploy.manager.entities.applications.dtos.ApplicationDTO;
import com.deploy.manager.entities.applications.model.ApplicationModel;
import com.deploy.manager.entities.applications.services.ApplicationServices;

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
@RequestMapping("/apps")
public class ApplicationsController {
	@Autowired
	private ApplicationServices applicationServices;

	@PostMapping
	public ResponseEntity<ApplicationModel> createOne(@RequestBody @Valid ApplicationDTO applicationDTO) {

		ApplicationModel response = applicationServices.create(applicationDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApplicationModel> updateById(@PathVariable(value = "id") Long id, @RequestBody @Valid ApplicationDTO applicationDTO) {

		var application = applicationServices.updateById(id, applicationDTO);
		return ResponseEntity.ok().body(application);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable(value = "id") Long id) {
		var msg = applicationServices.deleteById(id);
		return ResponseEntity.ok().body(msg);
	}

	@GetMapping
	public ResponseEntity<List<ApplicationDTO>> findAll() {
		var apps = applicationServices.findAll();
		return ResponseEntity.ok().body(apps);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApplicationDTO> findOneById(@PathVariable(value = "id") Long id) {
		var app = applicationServices.findById(id);
		return (ResponseEntity<ApplicationDTO>) ResponseEntity.ok().body(app);
	}

	@GetMapping("page")
	public ResponseEntity<Page<ApplicationDTO>> findAllByPage(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		var page = applicationServices.findAllByPage(pageable);
		return ResponseEntity.ok().body(page);
	}
}