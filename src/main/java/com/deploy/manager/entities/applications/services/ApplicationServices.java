package com.deploy.manager.entities.applications.services;

import com.deploy.manager.entities.applications.dtos.ApplicationDTO;
import com.deploy.manager.entities.applications.model.ApplicationModel;
import com.deploy.manager.entities.applications.repository.ApplicationRepository;

import com.deploy.manager.infra.HandlerErros.NotFoundCustomException.NotFoundCustomException;
import jakarta.transaction.Transactional;
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
public class ApplicationServices {
	@Autowired
	private ApplicationRepository applicationRepository;

	@Transactional
	public ApplicationModel save(ApplicationModel applicationModel) {
		return applicationRepository.save(applicationModel);
	}

	@Transactional
	public ApplicationModel updateById(Long id, ApplicationModel applicationModel) {
		validateIfApplicationNotExistsById(id);
		return applicationRepository.save(applicationModel);
		
	}

	public List<ApplicationDTO> findAll() {

		List<ApplicationModel> applications = applicationRepository.findAll();
		List<ApplicationDTO> applicationDTOs = new ArrayList<>();

		for (ApplicationModel application : applications) {
			ApplicationDTO applicationDTO = convertModelToApplicationDTO(application);
			applicationDTOs.add(applicationDTO);
		}
		return applicationDTOs;
	}

	public Page<ApplicationDTO> findAllByPage(Pageable pageable) {
		Page<ApplicationModel> pages = applicationRepository.findAll(pageable);

		List<ApplicationDTO> applicationDTOs = pages.getContent().stream()
				.map(this::convertModelToApplicationDTO)
				.collect(Collectors.toList());

		return new PageImpl<>(applicationDTOs, pageable, pages.getTotalElements());
	}

	public ApplicationDTO findById(Long id) {
		validateIfApplicationNotExistsById(id);

		Optional<ApplicationModel> applicationModelOptional = applicationRepository.findById(id);
		ApplicationModel applicationModel = applicationModelOptional.get();
		return convertModelToApplicationDTO(applicationModel);
	}

	public void deleteById(Long id) {
		validateIfApplicationNotExistsById(id);
		applicationRepository.deleteById(id);
	}


	private ApplicationDTO convertModelToApplicationDTO(ApplicationModel applicationModel) {
		ApplicationDTO applicationDTO = new ApplicationDTO();
		applicationDTO.nameApplication = applicationModel.getNameApplication();
		applicationDTO.setId(applicationModel.getId());
		applicationDTO.setNameApplication(applicationModel.getNameApplication());
		applicationDTO.setDescription(applicationModel.getDescription());

		return applicationDTO;
	}

	private void validateIfApplicationNotExistsById(Long id) {
		Optional<ApplicationModel> applicationFound = applicationRepository.findById(id);
		if (applicationFound.isEmpty()) {
			throw new NotFoundCustomException("Application not found with id: " + id);
		}
	}
}

