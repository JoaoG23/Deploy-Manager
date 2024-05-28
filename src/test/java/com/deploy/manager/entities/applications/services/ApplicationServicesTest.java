package com.deploy.manager.entities.applications.services;

import com.deploy.manager.entities.applications.dtos.ApplicationDTO;
import com.deploy.manager.entities.applications.model.ApplicationModel;
import com.deploy.manager.entities.applications.repository.ApplicationRepository;
import com.deploy.manager.infra.HandlerErros.NotFoundCustomException.NotFoundCustomException;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
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
	@DisplayName("Should find all")
	void findAllCase1()  {

		ApplicationModel appModel = new ApplicationModel();
		appModel.setId(1L);
		appModel.setNameApplication("App");
		appModel.setDescription("Descrição da aplicação");

		// Define o comportamento do método findAll() para retornar uma lista com o appModel
		when(applicationRepository.findAll()).thenReturn(Arrays.asList(appModel));

		var result = applicationServices.findAll();

		assertThat(result.isEmpty()).isFalse();
	}
	@Test
	@DisplayName("Should throw Exception when application don't exists ")
	void updateApplicationCase1() throws Exception {

		ApplicationDTO app = new ApplicationDTO();
		app.setId(2L);
		app.setNameApplication("App");
		app.setDescription("Descrição da aplicação");

		// Capturar a excessao
		Exception thrown = Assertions.assertThrows(NotFoundCustomException.class, ()-> {
			applicationServices.updateById(1L, app);
		});
		Assertions.assertEquals("Application not found with id: 1", thrown.getMessage());
	}

	@Test
	@DisplayName("Should update one app for id")
	void updateApplicationCase2() throws Exception {

		Long idTested = 1L;

		ApplicationModel appModel = new ApplicationModel();
		appModel.setId(idTested);
		appModel.setNameApplication("App");
		appModel.setDescription("Descrição da aplicação");

		when(applicationRepository.findById(idTested)).thenReturn(Optional.of(appModel));

		var appFound = applicationServices.updateById(idTested,applicationReturned());
		verify(applicationRepository, times(1)).save(any());
	}
	@Test
	@DisplayName("Should to delete one for id")
	@Tag("delete")
	void deleteApplicationCaseOne() throws Exception {
		Long idTested = 1L;

		ApplicationModel appModel = new ApplicationModel();
		appModel.setId(idTested);
		appModel.setNameApplication("App");
		appModel.setDescription("Descrição da aplicação");

		// Para quando essa função for executada
		// Caso de sucesso retorna algo e não gerar uma exeção
		when(applicationRepository.findById(idTested)).thenReturn(Optional.of(appModel));

		applicationServices.deleteById(idTested);
		verify(applicationRepository, times(1)).deleteById(any());
	}

	@Test
	@DisplayName("Should throw exception if not exists application")
	void deleteApplicationCase2() throws Exception {

		ApplicationDTO app = new ApplicationDTO();
		app.setId(2L);
		app.setNameApplication("App");
		app.setDescription("Descrição da aplicação");

		// Capturar a excessao
		Exception thrown = Assertions.assertThrows(NotFoundCustomException.class, ()-> {
			applicationServices.deleteById(1L);
		});
		Assertions.assertEquals("Application not found with id: 1", thrown.getMessage());
	}



	private ApplicationDTO applicationReturned() {
		ApplicationDTO app = new ApplicationDTO();
		app.setId(1L);
		app.setNameApplication("App");
		app.setDescription("Descrição da aplicação");
		return app;
	}

}