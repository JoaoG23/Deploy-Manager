package com.deploy.manager.entities.applicationscompanies.services;

import com.deploy.manager.entities.applicationscompanies.dtos.ApplicationCompanyDTO;
import com.deploy.manager.entities.applicationscompanies.model.ApplicationCompanyModel;
import com.deploy.manager.entities.applicationscompanies.repository.ApplicationCompanyRepository;
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

	@Transactional
	public ApplicationCompanyModel create(ApplicationCompanyDTO applicationCompanyDTO) {
		ApplicationCompanyModel applicationCompany = new ApplicationCompanyModel();
		BeanUtils.copyProperties(applicationCompanyDTO, applicationCompany);
		return applicationCompanyRepository.save(applicationCompany);
	}

	@Transactional
	public ApplicationCompanyModel updateById(Long id, ApplicationCompanyDTO applicationCompanyDTO) {
		validateIfApplicationCompanyNotExistsById(id);
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
		validateIfApplicationCompanyNotExistsById(id);
		Optional<ApplicationCompanyModel> applicationCompanyOptional = applicationCompanyRepository.findById(id);
		ApplicationCompanyModel applicationCompany = applicationCompanyOptional.get();
		return convertModelToDTO(applicationCompany);
	}

	public String deleteById(Long id) {
		validateIfApplicationCompanyNotExistsById(id);
		applicationCompanyRepository.deleteById(id);
		return "ApplicationCompany deleted " + id;
	}

	private ApplicationCompanyDTO convertModelToDTO(ApplicationCompanyModel applicationCompany) {
		ApplicationCompanyDTO applicationCompanyDTO = new ApplicationCompanyDTO();
		applicationCompanyDTO.setId(applicationCompany.getId());
		applicationCompanyDTO.setIdCompanies(applicationCompany.getCompany().getId());
		applicationCompanyDTO.setIdApplications(applicationCompany.getApplication().getId());
		applicationCompanyDTO.setNotesFrontend(applicationCompany.getNotesFrontend());
		applicationCompanyDTO.setNotesBackend(applicationCompany.getNotesBackend());
		applicationCompanyDTO.setStatus(applicationCompany.getStatus());
		return applicationCompanyDTO;
	}

	private void validateIfApplicationCompanyNotExistsById(Long id) {
		Optional<ApplicationCompanyModel> applicationCompanyFound = applicationCompanyRepository.findById(id);
		if (applicationCompanyFound.isEmpty()) {
			throw new NotFoundCustomException("ApplicationCompany not found with id: " + id);
		}
	}
}
