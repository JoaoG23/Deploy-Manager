package com.deploy.manager.entities.applicationscompanies.repository;

import com.deploy.manager.entities.applicationscompanies.model.ApplicationCompanyModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationCompanyRepository extends JpaRepository<ApplicationCompanyModel, Long> {}
