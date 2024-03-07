package com.deploy.manager.controllers;


import com.deploy.manager.dtos.users.CreateUserDTO;
import com.deploy.manager.models.UserModel;
import com.deploy.manager.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserRepository repository;

	@PostMapping
	public void cadastrar(@RequestBody CreateUserDTO userDTO) {
		UserModel userModel = new UserModel(userDTO);
		BeanUtils.copyProperties(userDTO, userModel);

		repository.save(userModel);
		System.out.println(userDTO);
	}
}
