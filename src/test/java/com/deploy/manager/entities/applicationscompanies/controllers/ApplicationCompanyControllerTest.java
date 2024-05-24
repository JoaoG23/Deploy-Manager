package com.deploy.manager.entities.applicationscompanies.controllers;

import com.deploy.manager.entities.applications.dtos.ApplicationDTO;
import com.deploy.manager.entities.applications.model.ApplicationModel;
import com.deploy.manager.entities.applications.repository.ApplicationRepository;
import com.deploy.manager.entities.applicationscompanies.dtos.ApplicationCompanyDTO;
import com.deploy.manager.entities.applicationscompanies.enums.StatusApplication;
import com.deploy.manager.entities.applicationscompanies.model.ApplicationCompanyModel;
import com.deploy.manager.entities.applicationscompanies.repository.ApplicationCompanyRepository;
import com.deploy.manager.entities.companies.dtos.CompanyDTO;
import com.deploy.manager.entities.companies.model.CompanyModel;
import com.deploy.manager.entities.companies.repository.CompanyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
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
		System.out.println(applicationCompanyDTO);
		var teste = mockMvc.perform(MockMvcRequestBuilders.post("/application-companies")
						.contentType(MediaType.APPLICATION_JSON)
						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
						.content(objectMapper.writeValueAsString(applicationCompanyDTO)))
				.andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.nameApplication").value("App"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Descrição da aplicação"));
		System.out.println(teste);
	}

//	@Test
//	@DisplayName("Update an app successfully and return 200")
//	void updateOneCase1() throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//		String applicationString = createApplication();
//
//		// Transform in JSON object
//		JSONObject application = new JSONObject(applicationString);
//		// Access the 'id' property
//		Long id = Long.parseLong(application.getString("id"));
//		ApplicationCompanyDTO applicationCompanyDTO = applicationForCompanyReturned();
//
//		mockMvc.perform(MockMvcRequestBuilders.put("/application-companies/{id}", id)
//						.contentType(MediaType.APPLICATION_JSON)
//						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
//						.content(objectMapper.writeValueAsString(applicationCompanyDTO)))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.nameApplication").value("App"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Descrição da aplicação"));
//	}

//	@Test
//	@DisplayName("Delete one app with success and return 200")
//	void deleteOneById() throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//		// Create App
//		String applicationString = createApplication();
//
//		JSONObject application = new JSONObject(applicationString);
//
//		// Extract Id
//		Long id = Long.parseLong(application.getString("id"));
//		ApplicationCompanyDTO applicationCompanyDTO = applicationForCompanyReturned();
//
//		mockMvc.perform(MockMvcRequestBuilders.delete("/application-companies/{id}", id)
//						.contentType(MediaType.APPLICATION_JSON)
//						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.content().string("Application deleted " + id));
//
//	}

//	@Test
//	@DisplayName("Find all application-companies and return success 200")
//	void findAllCase1() throws Exception {
//		createApplication();
//
//		mockMvc.perform(MockMvcRequestBuilders.get("/application-companies")
//						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("$[0].nameApplication").value("App"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value("Descrição da aplicação"));
//	}

//	@Test
//	@DisplayName("Find all by page and return success 200")
//	void findByPageCase2() throws Exception {
//		createApplication();
//		createApplication();
//		mockMvc.perform(MockMvcRequestBuilders.get("/application-companies/page")
//						.param("page", "0")
//						.param("size", "1")
//						.param("sort", "id")
//						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(1));
//	}

//	@Test
//	@DisplayName("Find one by id and return success 200")
//	void findOneByIdCase1() throws Exception {
//		ObjectMapper objectMapper = new ObjectMapper();
//		// Create App
//		String applicationString = createApplication();
//
//		JSONObject application = new JSONObject(applicationString);
//
//		// Extract Id
//		Long id = Long.parseLong(application.getString("id"));
//		ApplicationCompanyDTO applicationCompanyDTO = applicationForCompanyReturned();
//
//		mockMvc.perform(MockMvcRequestBuilders.get("/application-companies/{id}", id)
//						.header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
//				.andExpect(MockMvcResultMatchers.status().isOk())
//				.andExpect(MockMvcResultMatchers.jsonPath("$.nameApplication").value("App"))
//				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Descrição da aplicação"));
//	}

	private String createApplicationForCompany(Long idCompany, Long idApp) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		var applicationCompanyModel = new ApplicationCompanyModel();

		ApplicationCompanyDTO applicationCompanyDTO = applicationForCompanyReturned(idCompany, idApp);
		BeanUtils.copyProperties(applicationCompanyDTO, applicationCompanyModel);

		applicationCompanyRepository.save(applicationCompanyModel);

		return null;
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
		appForCompany.setIdCompanies(idCompany);
		appForCompany.setIdApplications(idApp);
		appForCompany.setNotesFrontend("Front");
		appForCompany.setNotesBackend("Back");
		appForCompany.setStatus(StatusApplication.valueOf("PARADO"));
		return appForCompany;
	}
}
