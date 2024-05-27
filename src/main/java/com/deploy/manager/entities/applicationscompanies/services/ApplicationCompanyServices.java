package com.deploy.manager.entities.applicationscompanies.services;

import com.deploy.manager.entities.applications.repository.ApplicationRepository;
import com.deploy.manager.entities.applicationscompanies.dtos.ApplicationCompanyDTO;
import com.deploy.manager.entities.applicationscompanies.model.ApplicationCompanyModel;
import com.deploy.manager.entities.applicationscompanies.repository.ApplicationCompanyRepository;
import com.deploy.manager.entities.companies.repository.CompanyRepository;
import com.deploy.manager.infra.HandlerErros.NotFoundCustomException.NotFoundCustomException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationCompanyServices {
	@Autowired
	private ApplicationCompanyRepository applicationCompanyRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Autowired
	private ApplicationRepository applicationRepository;

	@Transactional
	public ApplicationCompanyModel create(ApplicationCompanyDTO dto) {
		ApplicationCompanyModel model = new ApplicationCompanyModel();

		BeanUtils.copyProperties(dto, model);
		var modelValidated = validateIfApplicationCompanyExists(dto, model);

		return applicationCompanyRepository.save(modelValidated);
	}

	@Transactional
	public ApplicationCompanyModel updateById(Long id, ApplicationCompanyDTO applicationCompanyDTO) {
		validateIfContractNotExistsById(id);
		ApplicationCompanyModel applicationCompany = new ApplicationCompanyModel();
		applicationCompanyDTO.setId(id);
		BeanUtils.copyProperties(applicationCompanyDTO, applicationCompany);
		return applicationCompanyRepository.save(applicationCompany);
	}

	public List<ApplicationCompanyDTO> findAll() {
		List<ApplicationCompanyModel> applicationCompanies = applicationCompanyRepository.findAll();
		List<ApplicationCompanyDTO> applicationCompanyDTOs = new ArrayList<>();
		for (ApplicationCompanyModel applicationCompany : applicationCompanies) {
			ApplicationCompanyDTO applicationCompanyDTO = convertModelToDTO(applicationCompany);
			applicationCompanyDTOs.add(applicationCompanyDTO);
		}
		return applicationCompanyDTOs;
	}

	public Page<ApplicationCompanyDTO> findAllByPage(Pageable pageable) {
		Page<ApplicationCompanyModel> pages = applicationCompanyRepository.findAll(pageable);
		List<ApplicationCompanyDTO> applicationCompanyDTOs = pages.getContent().stream()
				.map(this::convertModelToDTO)
				.collect(Collectors.toList());
		return new PageImpl<>(applicationCompanyDTOs, pageable, pages.getTotalElements());
	}

	public ApplicationCompanyDTO findById(Long id) {
		validateIfContractNotExistsById(id);
		Optional<ApplicationCompanyModel> applicationCompanyOptional = applicationCompanyRepository.findById(id);
		ApplicationCompanyModel applicationCompany = applicationCompanyOptional.get();
		return convertModelToDTO(applicationCompany);
	}

	public String deleteById(Long id) {
		validateIfContractNotExistsById(id);
		applicationCompanyRepository.deleteById(id);
		return "Contract deleted of id " + id;
	}

	private ApplicationCompanyDTO convertModelToDTO(ApplicationCompanyModel model) {
		ApplicationCompanyDTO applicationCompanyDTO = new ApplicationCompanyDTO();
		applicationCompanyDTO.setId(model.getId());
		applicationCompanyDTO.setApplication(model.getApplication());
		applicationCompanyDTO.setCompany(model.getCompany());
		applicationCompanyDTO.setNotesFrontend(model.getNotesFrontend());
		applicationCompanyDTO.setNotesBackend(model.getNotesBackend());
		applicationCompanyDTO.setStatus(model.getStatus());
		return applicationCompanyDTO;
	}

	private void validateIfContractNotExistsById(Long id) {
		Optional<ApplicationCompanyModel> applicationCompanyFound = applicationCompanyRepository.findById(id);
		if (applicationCompanyFound.isEmpty()) {
			throw new NotFoundCustomException("Contract not found with id: " + id);
		}
	}

	private ApplicationCompanyModel validateIfApplicationCompanyExists(ApplicationCompanyDTO dto,ApplicationCompanyModel model) {
		if (dto.getCompanyId() != null) {
			var company = companyRepository.findById(dto.getCompanyId())
					.orElseThrow(() -> new NotFoundCustomException("Company not found"));
			model.setCompany(company);
		}

		if (dto.getApplicationId() != null) {
			var application = applicationRepository.findById(dto.getApplicationId())
					.orElseThrow(() -> new NotFoundCustomException("Application not found"));
			model.setApplication(application);
		}

		return model;

	}
}
