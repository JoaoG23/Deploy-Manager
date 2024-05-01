package com.deploy.manager.entities.applications.repository;

import com.deploy.manager.entities.applications.dtos.ApplicationDTO;
import com.deploy.manager.entities.applications.model.ApplicationModel;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
class ApplicationRepositoryTest {

	@Autowired
	ApplicationRepository applicationRepository;
	@Autowired
	EntityManager entityManager;

	@Test
	@DisplayName("Should get Application from database")
	public void findByIdCase1() {

		ApplicationDTO applicationDTO = new ApplicationDTO();
		applicationDTO.setNameApplication("Aplicacao Teste");
		applicationDTO.setDescription("Descrição da Aplicacao");

		this.createApplication(applicationDTO);

		Optional<ApplicationModel> result = applicationRepository.findById(1L);

		assertThat(result.isPresent()).isTrue();

	}

	@Test
	@DisplayName("Should get Application from database")
	public void findByIdcase2() {

		Optional<ApplicationModel> result = applicationRepository.findById(1L);

		assertThat(result.isEmpty()).isTrue();

	}

	// Metodos auxiliar para aplicação
	private ApplicationModel createApplication(ApplicationDTO applicationDTO) {

		var newApplication = new ApplicationModel();
		BeanUtils.copyProperties(applicationDTO, newApplication);

		this.entityManager.persist(newApplication);
		return newApplication;
	}
}