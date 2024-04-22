package com.deploy.manager.entities.applications.services;

import com.deploy.manager.entities.applications.model.ApplicationModel;
import com.deploy.manager.entities.applications.repository.ApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServices {

	@Autowired
	private ApplicationRepository applicationRepository;

	@Transactional
	public String createOne(ApplicationModel applicationModel) {
		applicationRepository.save(applicationModel);
		return "New application saved with success";
	}
}
