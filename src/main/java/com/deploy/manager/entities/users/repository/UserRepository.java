package com.deploy.manager.entities.users.repository;


import com.deploy.manager.entities.users.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
}
