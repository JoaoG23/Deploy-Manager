package com.deploy.manager.entities.applications.repository;

import com.deploy.manager.entities.applications.model.ApplicationModel;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;


@DataJpaTest
@ActiveProfiles("test")
class ApplicationRepositoryTest {

	@Autowired
	EntityManager entityManager;

	@Test
	void findById() {

	}

//	private ApplicationModel createApplication() {
//		ApplicationModel
//	}
}