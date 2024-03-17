package com.deploy.manager.entities.users.services;

import com.deploy.manager.entities.users.dtos.FindUserDTO;
import com.deploy.manager.entities.users.model.UserModel;
import com.deploy.manager.entities.users.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public String register(UserModel userModel) {
		userRepository.save(userModel);
		return "Usuário Salvo com sucesso";
	}

	public List<FindUserDTO> findAll() {

//		List<UserModel> usersDatabase = userRepository.findAll();
		return userRepository.findAll().stream().map(FindUserDTO::new).toList();
	}

	public Page<UserModel> findAllByPage(Pageable pageable) {
		return userRepository.findAll(pageable);
	}


}
