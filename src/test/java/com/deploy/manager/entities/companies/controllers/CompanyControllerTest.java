package com.deploy.manager.entities.companies.controllers;

import com.deploy.manager.entities.companies.dtos.CompanyDTO;
import com.deploy.manager.entities.companies.repository.CompanyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
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


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CompanyControllerTest {
	private final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkZXBsb3lzX21hbmFnZXJfYXBpIiwiaWF0IjoxNzE1MDkzMTIwLCJleHAiOjE5MDQzOTU3NjIsImF1ZCI6IiIsInN1YiI6ImpvYW8ifQ.WPZ7jg4n-iwrQ5lQJcSDBGjBj_0uMwo7WLcTOdTFmRI"; // replace this with your actual JWT

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CompanyRepository companyRepository;


	@BeforeEach
	@AfterEach
	void deleteAll() {
		companyRepository.deleteAll();
	}

	@Test
	@DisplayName("Create one company with success and return 204")
	void createOneCase1() throws Exception {
		// populate this with test data
		ObjectMapper objectMapper = new ObjectMapper();

		var companyDTO = companyReturned();

		mockMvc.perform(MockMvcRequestBuilders.post("/companies")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token) // add JWT in Authorization header
						.content(objectMapper.writeValueAsString(companyDTO))) // send CompanyDTO as JSON in the request body
				.andExpect(MockMvcResultMatchers.status().isCreated())// expect CREATED status
				.andExpect(MockMvcResultMatchers.jsonPath("$.nameCompany").value("Company"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Descrição da empresa"));
	}

	@Test
	@DisplayName("Update a company successfully and return 200")
	void updateOneCase1() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String companyString = createCompany();

		// Transform in JSON object
		JSONObject company = new JSONObject(companyString);
		// Access the 'id' property
		Long id = Long.parseLong(company.getString("id"));
		CompanyDTO companyDTO = companyReturned();

		mockMvc.perform(MockMvcRequestBuilders.put("/companies/{id}", id)
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
						.content(objectMapper.writeValueAsString(companyDTO)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
				.andExpect(MockMvcResultMatchers.jsonPath("$.nameCompany").value("Company"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Descrição da empresa"));
	}

	@Test
	@DisplayName("Delete one company with success and return 200")
	void deleteOneById() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		// Create Company
		String companyString = createCompany();

		JSONObject company = new JSONObject(companyString);

		// Extract Id
		Long id = Long.parseLong(company.getString("id"));
		CompanyDTO companyDTO = companyReturned();

		mockMvc.perform(MockMvcRequestBuilders.delete("/companies/{id}", id)
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Company deleted " + id));

	}

	@Test
	@DisplayName("Find all companies and return success 200")
	void findAllCase1() throws Exception {
		createCompany();

		mockMvc.perform(MockMvcRequestBuilders.get("/companies")
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].nameCompany").value("Company"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Descrição da empresa"));
	}

	@Test
	@DisplayName("Find all by page and return success 200")
	void findByPageCase2() throws Exception {
		createCompany();
		createCompany();
		mockMvc.perform(MockMvcRequestBuilders.get("/companies/page")
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
		// Create Company
		String companyString = createCompany();

		JSONObject company = new JSONObject(companyString);

		// Extract Id
		Long id = Long.parseLong(company.getString("id"));
		CompanyDTO companyDTO = companyReturned();

		mockMvc.perform(MockMvcRequestBuilders.get("/companies/{id}", id)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nameCompany").value("Company"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Descrição da empresa"));
	}

	private String createCompany() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		CompanyDTO companyDTO = companyReturned();

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/companies")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
						.content(objectMapper.writeValueAsString(companyDTO)))
				.andReturn(); // get the MvcResult

		String response = mvcResult
				.getResponse()
				.getContentAsString();
		return response;
	}


	private CompanyDTO companyReturned() {
		CompanyDTO company = new CompanyDTO();
		company.setNameCompany("Company");
		company.setDescription("Descrição da empresa");
		return company;
	}
}
