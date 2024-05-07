package com.deploy.manager.entities.applications.controllers;

import com.deploy.manager.entities.applications.dtos.ApplicationDTO;
import com.deploy.manager.entities.applications.repository.ApplicationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationsControllerTest {

	private final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkZXBsb3lzX21hbmFnZXJfYXBpIiwiaWF0IjoxNzE1MDkzMTIwLCJleHAiOjE5MDQzOTU3NjIsImF1ZCI6IiIsInN1YiI6ImpvYW8ifQ.WPZ7jg4n-iwrQ5lQJcSDBGjBj_0uMwo7WLcTOdTFmRI"; // replace this with your actual JWT

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ApplicationRepository applicationRepository;

	@BeforeEach
	void deleteAll() {
		applicationRepository.deleteAll();
	}

	@Test
	void createOneCase1() throws Exception {
		 // populate this with test data
		ObjectMapper objectMapper = new ObjectMapper();

		var applicationDTO = applicationReturned();

		mockMvc.perform(MockMvcRequestBuilders.post("/apps")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token) // add JWT in Authorization header
						.content(objectMapper.writeValueAsString(applicationDTO))) // send ApplicationDTO as JSON in the request body
				.andExpect(MockMvcResultMatchers.status().isCreated())// expect CREATED status
				.andExpect(MockMvcResultMatchers.jsonPath("$.nameApplication").value("App"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Descrição da aplicação"));
	}

	private ApplicationDTO applicationReturned() {
		ApplicationDTO app = new ApplicationDTO();
		app.setNameApplication("App");
		app.setDescription("Descrição da aplicação");
		return app;
	}
}