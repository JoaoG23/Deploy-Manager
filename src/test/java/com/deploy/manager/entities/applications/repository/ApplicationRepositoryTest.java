package com.deploy.manager.entities.applications.repository;

import com.deploy.manager.entities.applications.dtos.ApplicationDTO;
import com.deploy.manager.entities.applications.model.ApplicationModel;
import jakarta.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;


@DataJpaTest
@ActiveProfiles("test")
class ApplicationRepositoryTest {

	@Autowired
	ApplicationRepository applicationRepository;
	@Autowired
	EntityManager entityManager;

	@Test
	@DisplayName("Should get Application from database")
	public void findById() {

		ApplicationDTO applicationDTO = new ApplicationDTO();
		applicationDTO.setNameApplication("Aplicacao Teste");
		applicationDTO.setDescription("Descrição da Aplicacao");

		this.createApplication(applicationDTO);

		Optional<ApplicationModel> result = applicationRepository.findById(1L);

		assertThat(result.isPresent()).isTrue();

	}


	// Metodos auxiliar para aplicação
	private ApplicationModel createApplication(ApplicationDTO applicationDTO) {

		var newApplication = new ApplicationModel();
		BeanUtils.copyProperties(applicationDTO, newApplication);

		System.out.println(newApplication);
		this.entityManager.persist(newApplication);
		return newApplication;
	}


}