package com.deploy.manager.entities.companies.services;

import com.deploy.manager.entities.companies.dtos.CompanyDTO;
import com.deploy.manager.entities.companies.model.CompanyModel;

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
public class CompanyServices {
	@Autowired
	private CompanyRepository companyRepository;

	@Transactional
	public CompanyModel create(CompanyDTO companyDTO) {

		CompanyModel companyModel = new CompanyModel();
		BeanUtils.copyProperties(companyDTO, companyModel);
		return companyRepository.save(companyModel);
	}

	@Transactional
	public CompanyModel updateById(Long id, CompanyDTO companyDTO) {
		validateIfCompanyNotExistsById(id);
		CompanyModel companyModel = new CompanyModel();

		companyDTO.setId(id);
		BeanUtils.copyProperties(companyDTO, companyModel);
		return companyRepository.save(companyModel);

	}

	public List<CompanyDTO> findAll() {

		List<CompanyModel> companies = companyRepository.findAll();
		List<CompanyDTO> companyDTOs = new ArrayList<>();

		for (CompanyModel company : companies) {
			CompanyDTO companyDTO = convertModelToCompanyDTO(company);
			companyDTOs.add(companyDTO);
		}
		return companyDTOs;
	}

	public Page<CompanyDTO> findAllByPage(Pageable pageable) {
		Page<CompanyModel> pages = companyRepository.findAll(pageable);

		List<CompanyDTO> companyDTOs = pages.getContent().stream()
				.map(this::convertModelToCompanyDTO)
				.collect(Collectors.toList());

		return new PageImpl<>(companyDTOs, pageable, pages.getTotalElements());
	}

	public CompanyDTO findById(Long id) {
		validateIfCompanyNotExistsById(id);

		Optional<CompanyModel> companyModelOptional = companyRepository.findById(id);
		CompanyModel companyModel = companyModelOptional.get();
		return convertModelToCompanyDTO(companyModel);
	}

	public String deleteById(Long id) {
		validateIfCompanyNotExistsById(id);
		companyRepository.deleteById(id);
		return "Company deleted " + id;
	}


	private CompanyDTO convertModelToCompanyDTO(CompanyModel companyModel) {
		CompanyDTO companyDTO = new CompanyDTO();

		companyDTO.setId(companyModel.getId());
		companyDTO.setNameCompany(companyModel.getNameCompany());
		companyDTO.setDescription(companyModel.getDescription());

		return companyDTO;
	}

	private void validateIfCompanyNotExistsById(Long id) {
		Optional<CompanyModel> companyFound = companyRepository.findById(id);
		if (companyFound.isEmpty()) {
			throw new NotFoundCustomException("Company not found with id: " + id);
		}
	}
}
