package com.deploy.manager.entities.users.repository;


import com.deploy.manager.entities.users.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
	UserDetails findByUsername(String username);
}
