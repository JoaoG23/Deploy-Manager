package com.deploy.manager.entities.applications.controllers;

import com.deploy.manager.entities.applications.dtos.ApplicationDTO;
import com.deploy.manager.entities.applications.repository.ApplicationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApplicationsControllerTest {
	private final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkZXBsb3lzX21hbmFnZXJfYXBpIiwiaWF0IjoxNzE1MDkzMTIwLCJleHAiOjE5MDQzOTU3NjIsImF1ZCI6IiIsInN1YiI6ImpvYW8ifQ.WPZ7jg4n-iwrQ5lQJcSDBGjBj_0uMwo7WLcTOdTFmRI"; // replace this with your actual JWT

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ApplicationRepository applicationRepository;

	@BeforeEach
	@AfterEach
	void deleteAll() {
		applicationRepository.deleteAll();
	}


	@Test
	@DisplayName("Create one app with success and return 204")
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

	@Test
	@DisplayName("Update an app successfully and return 200")
	void updateOneCase1() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String applicationString = createApplication();

		// Transform in JSON object
		JSONObject application = new JSONObject(applicationString);
		// Access the 'id' property
		Long id = Long.parseLong(application.getString("id"));
		ApplicationDTO applicationDTO = applicationReturned();

		mockMvc.perform(MockMvcRequestBuilders.put("/apps/{id}", id)
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
						.content(objectMapper.writeValueAsString(applicationDTO)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nameApplication").value("App"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Descrição da aplicação"));
	}

	@Test
	@DisplayName("Delete one app with success and return 200")
	void deleteOneById() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		// Create App
		String applicationString = createApplication();

		JSONObject application = new JSONObject(applicationString);

		// Extract Id
		Long id = Long.parseLong(application.getString("id"));
		ApplicationDTO applicationDTO = applicationReturned();

		mockMvc.perform(MockMvcRequestBuilders.delete("/apps/{id}", id)
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Application deleted " + id));

	}

	@Test
	@DisplayName("Find all apps and return success 200")
	void findAllCase1() throws Exception {
		createApplication();

		mockMvc.perform(MockMvcRequestBuilders.get("/apps")
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].nameApplication").value("App"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Descrição da aplicação"));
	}
	@Test
	@DisplayName("Find all by page and return success 200")
	void findByPageCase2() throws Exception {
		createApplication();
		createApplication();
		mockMvc.perform(MockMvcRequestBuilders.get("/apps/page")
						.param("page", "0")
						.param("size", "1")
						.param("sort", "id")
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(1));
	}
	@Test
	@DisplayName("Find one by id and return success 200")
	void findOneByIdCase1() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		// Create App
		String applicationString = createApplication();

		JSONObject application = new JSONObject(applicationString);

		// Extract Id
		Long id = Long.parseLong(application.getString("id"));
		ApplicationDTO applicationDTO = applicationReturned();

		mockMvc.perform(MockMvcRequestBuilders.get("/apps/{id}", id)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nameApplication").value("App"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Descrição da aplicação"));
	}


	private String createApplication() throws Exception {
	/*
	Created this way for
	don't instance service Class
	 */
		ObjectMapper objectMapper = new ObjectMapper();
		ApplicationDTO applicationDTO = applicationReturned();

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/apps")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
						.content(objectMapper.writeValueAsString(applicationDTO)))
						.andReturn(); // get the MvcResult

		String response = mvcResult
				.getResponse()
				.getContentAsString();
		return response;
	}


	private ApplicationDTO applicationReturned() {
		ApplicationDTO app = new ApplicationDTO();
		app.setNameApplication("App");
		app.setDescription("Descrição da aplicação");
		return app;
	}
}