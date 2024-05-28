package com.deploy.manager.entities.applicationscompanies.controllers;

import com.deploy.manager.entities.applications.dtos.ApplicationDTO;
import com.deploy.manager.entities.applications.model.ApplicationModel;
import com.deploy.manager.entities.applications.repository.ApplicationRepository;
import com.deploy.manager.entities.applicationscompanies.dtos.ApplicationCompanyDTO;
import com.deploy.manager.entities.applicationscompanies.enums.StatusApplication;
import com.deploy.manager.entities.applicationscompanies.repository.ApplicationCompanyRepository;
import com.deploy.manager.entities.companies.dtos.CompanyDTO;
import com.deploy.manager.entities.companies.model.CompanyModel;
import com.deploy.manager.entities.companies.repository.CompanyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationCompanyControllerTest {
	private final String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJkZXBsb3lzX21hbmFnZXJfYXBpIiwiaWF0IjoxNzE1MDkzMTIwLCJleHAiOjE5MDQzOTU3NjIsImF1ZCI6IiIsInN1YiI6ImpvYW8ifQ.WPZ7jg4n-iwrQ5lQJcSDBGjBj_0uMwo7WLcTOdTFmRI"; // replace this with your actual JWT

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ApplicationCompanyRepository applicationCompanyRepository;

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private CompanyRepository companyRepository;

	@BeforeEach
	@AfterEach
	void deleteAll() {
		applicationCompanyRepository.deleteAll();
		applicationRepository.deleteAll();
		companyRepository.deleteAll();
	}


	@Test
	@DisplayName("Create one app for compay with success and return 204")
	void createOneCase1() throws Exception {
		// populate this with test data
		ObjectMapper objectMapper = new ObjectMapper();

		var company = insertCompany();
		var app = insertApplication();

		var applicationCompanyDTO = applicationForCompanyReturned(company.getId(), app.getId());
		mockMvc.perform(MockMvcRequestBuilders.post("/application-companies")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
						.content(objectMapper.writeValueAsString(applicationCompanyDTO)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.company").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.application").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.notesFrontend").value(applicationCompanyDTO.getNotesFrontend()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.notesBackend").value(applicationCompanyDTO.getNotesBackend()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("" + applicationCompanyDTO.getStatus() + ""));


//
	}

	@Test
	@DisplayName("Update an contract successfully and return 200")
	void updateOneCase1() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		var company = insertCompany();
		var app = insertApplication();

		String contractString = createApplicationForCompany(company.getId(), app.getId());
		JSONObject contract = new JSONObject(contractString);

		Long id = Long.parseLong(contract.getString("id"));

		var appForCompany = new ApplicationCompanyDTO();
		appForCompany.setCompanyId(company.getId());
		appForCompany.setApplicationId(app.getId());
		appForCompany.setNotesFrontend("EDITADO Front");
		appForCompany.setNotesBackend("EDITADO Back");
		appForCompany.setStatus(StatusApplication.valueOf("RODANDO"));

		mockMvc.perform(MockMvcRequestBuilders.put("/application-companies/{id}", id)
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
						.content(objectMapper.writeValueAsString(appForCompany)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
				.andExpect(MockMvcResultMatchers.jsonPath("$.company").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.application").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.notesFrontend").value("EDITADO Front"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.notesBackend").value("EDITADO Back"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("RODANDO"));
	}

	@Test
	@DisplayName("Erro to update a contract that don't exist and return 400")
	void updateOneCase2() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		Long id = 10L;

		var appForCompany = new ApplicationCompanyDTO();
		appForCompany.setCompanyId(1L);
		appForCompany.setApplicationId(1L);
		appForCompany.setNotesFrontend("EDITADO Front");
		appForCompany.setNotesBackend("EDITADO Back");
		appForCompany.setStatus(StatusApplication.valueOf("RODANDO"));

		mockMvc.perform(MockMvcRequestBuilders.put("/application-companies/{id}", id)
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
						.content(objectMapper.writeValueAsString(appForCompany)))
				.andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(MockMvcResultMatchers.content().string("Contract not found with id: " + id));
	}

	@Test
	@DisplayName("Delete one app with success and return 200")
	void deleteOneById() throws Exception {
		var company = insertCompany();
		var app = insertApplication();

		String contractString = createApplicationForCompany(company.getId(), app.getId());
		JSONObject contract = new JSONObject(contractString);

		Long id = Long.parseLong(contract.getString("id"));

		mockMvc.perform(MockMvcRequestBuilders.delete("/application-companies/{id}", id)
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Contract deleted of id " + id));
	}

	@Test
	@DisplayName("Should find all application-companies and return success 200")
	void findAllCase1() throws Exception {

		var company = insertCompany();
		var app = insertApplication();

		createApplicationForCompany(company.getId(), app.getId());

		mockMvc.perform(MockMvcRequestBuilders.get("/application-companies")
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(1));

	}

	@Test
	@DisplayName("Find all by page and return success 200")
	void findByPageCase2() throws Exception {
		var company = insertCompany();
		var app = insertApplication();

		createApplicationForCompany(company.getId(), app.getId());
		createApplicationForCompany(company.getId(), app.getId());

		mockMvc.perform(MockMvcRequestBuilders.get("/application-companies/page")
						.param("page", "0")
						.param("size", "2")
						.param("sort", "id")
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(2));
	}


	@Test
	@DisplayName("Find one by id and return success 200")
	void findOneByIdCase1() throws Exception {

		var company = insertCompany();
		var app = insertApplication();

		String contractString = createApplicationForCompany(company.getId(), app.getId());
		JSONObject contract = new JSONObject(contractString);

		Long id = Long.parseLong(contract.getString("id"));

		mockMvc.perform(MockMvcRequestBuilders.get("/application-companies/{id}", id)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.company.nameCompany").value(company.getNameCompany()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.company.description").value(company.getDescription()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.application.nameApplication").value(app.getNameApplication()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.application.description").value(app.getDescription()))
				.andExpect(MockMvcResultMatchers.jsonPath("$.notesFrontend").value("Front"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.notesBackend").value("Back"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("PARADO"));
	}


	private String createApplicationForCompany(Long companyId, Long applicationId) throws Exception {
	/*
	Created this way for
	don't instance service Class
	 */
		ObjectMapper objectMapper = new ObjectMapper();
		var contract = applicationForCompanyReturned(companyId, applicationId);

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/application-companies")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
						.content(objectMapper.writeValueAsString(contract)))
				.andReturn(); // get the MvcResult

		return mvcResult
				.getResponse()
				.getContentAsString();
	}


	private ApplicationModel insertApplication() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		var applicationModel = new ApplicationModel();

		var applicationDTO = applicationReturned();
		BeanUtils.copyProperties(applicationDTO, applicationModel);

		return applicationRepository.save(applicationModel);
	}

	private ApplicationDTO applicationReturned() {
		ApplicationDTO app = new ApplicationDTO();
		app.setNameApplication("App");
		app.setDescription("Descrição da aplicação");
		return app;
	}

	private CompanyModel insertCompany() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		var companyModel = new CompanyModel();

		var companyDTO = companyReturned();
		BeanUtils.copyProperties(companyDTO, companyModel);
		return companyRepository.save(companyModel);
	}

	private CompanyDTO companyReturned() {
		CompanyDTO company = new CompanyDTO();
		company.setNameCompany("Company");
		company.setDescription("Descrição da empresa");
		return company;
	}

	private ApplicationCompanyDTO applicationForCompanyReturned(Long idCompany, Long idApp) {
		ApplicationCompanyDTO appForCompany = new ApplicationCompanyDTO();
		appForCompany.setCompanyId(idCompany);
		appForCompany.setApplicationId(idApp);
		appForCompany.setNotesFrontend("Front");
		appForCompany.setNotesBackend("Back");
		appForCompany.setStatus(StatusApplication.valueOf("PARADO"));
		return appForCompany;
	}
}
