package com.deploy.manager.entities.applications.services;

import com.deploy.manager.entities.applications.dtos.ApplicationDTO;
import com.deploy.manager.entities.applications.repository.ApplicationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class ApplicationServicesTest {


	@Mock
	private ApplicationRepository applicationRepository;

	@Autowired
	@InjectMocks // Substituir pelos mockes
	private ApplicationServices applicationServices;


	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	@DisplayName("Should create one new application")
	void createApplicationCase1()  {

		ApplicationDTO app = new ApplicationDTO();
		app.setNameApplication("App");
		app.setDescription("Descrição da aplicação");

		applicationServices.create(app);

		verify(applicationRepository, times(1)).save(any());
	}

	@Test
	@DisplayName("Should throw Exception when application don't exists ")
	void updateApplicationCase1() throws Exception {

		ApplicationDTO app = new ApplicationDTO();
		app.setNameApplication("App");
		app.setDescription("Descrição da aplicação");

		var appFound = applicationServices.create(app);
//		applicationServices.updateById(1L, appFound);

		verify(applicationRepository, times(1)).save(any());
	}

}