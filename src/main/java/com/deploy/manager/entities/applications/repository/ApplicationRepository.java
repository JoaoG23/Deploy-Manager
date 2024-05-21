package com.deploy.manager.entities.applications.repository;

import com.deploy.manager.entities.applications.model.ApplicationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationModel, Long> {}
