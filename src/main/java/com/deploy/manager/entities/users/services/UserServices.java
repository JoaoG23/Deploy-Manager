package com.deploy.manager.entities.users.services;

import com.deploy.manager.entities.users.dtos.UserViewedDTO;
import com.deploy.manager.entities.users.model.UserModel;
import com.deploy.manager.entities.users.repository.UserRepository;
import com.deploy.manager.infra.HandlerErros.NotFoundCustomException.NotFoundCustomException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServices {
	@Autowired
	private UserRepository userRepository;

	@Transactional
	public String register(UserModel userModel) {
		var encoderPassword = new BCryptPasswordEncoder().encode(userModel.getPassword());
		userModel.setPassword(encoderPassword);

		userRepository.save(userModel);
		return "User saved with success";
	}

	@Transactional
	public String updateById(Long id, UserModel userModel) {
		validateIfUserNotExistsById(id);

		var encoderPassword = new BCryptPasswordEncoder().encode(userModel.getPassword());
		userModel.setPassword(encoderPassword);
		userModel.setId(id);

		userRepository.save(userModel);
		return "User updated with success";
	}

	public List<UserViewedDTO> findAll() {

		List<UserModel> users = userRepository.findAll();
		List<UserViewedDTO> userDTOs = new ArrayList<>();

		for (UserModel user : users) {
			UserViewedDTO userViewedDTO = convertModelToUserViewedDTO(user);
			userDTOs.add(userViewedDTO);
		}
		return userDTOs;
	}

	public Page<UserViewedDTO> findAllByPage(Pageable pageable) {
		Page<UserModel> userPage = userRepository.findAll(pageable);

		List<UserViewedDTO> userDTOs = userPage.getContent().stream()
				.map(this::convertModelToUserViewedDTO)
				.collect(Collectors.toList());

		return new PageImpl<>(userDTOs, pageable, userPage.getTotalElements());
	}

	public UserViewedDTO findById(Long id) {
		validateIfUserNotExistsById(id);

		Optional<UserModel> userModelOptional = userRepository.findById(id);
		UserModel userModel = userModelOptional.get();
		return convertModelToUserViewedDTO(userModel);
	}

	public void deleteById(Long id) {
		validateIfUserNotExistsById(id);

		userRepository.deleteById(id);
	}


	private UserViewedDTO convertModelToUserViewedDTO(UserModel userModel) {
		// Create a new UserDTO object and populate it with data from UserModel
		UserViewedDTO userDTO = new UserViewedDTO();
		userDTO.setId(userModel.getId());
		userDTO.setUsername(userModel.getUsername());
		userDTO.setFull_name(userModel.getFull_name());

		return userDTO;
	}

	private void validateIfUserNotExistsById(Long id) {
		Optional<UserModel> userFound = userRepository.findById(id);
		if (userFound.isEmpty()) {
			throw new NotFoundCustomException("User not found with id: " + id);
		}
	}
}
