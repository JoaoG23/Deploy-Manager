package com.deploy.manager.entities.applicationscompanies.repository;


import com.deploy.manager.entities.companies.model.CompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyModel, Long> {}
