package com.deploy.manager.entities.users.controllers;


import com.deploy.manager.entities.users.dtos.FindUserDTO;
import com.deploy.manager.entities.users.dtos.UserDTO;
import com.deploy.manager.entities.users.model.UserModel;
import com.deploy.manager.entities.users.services.UserServices;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserServices userServices;

	@PostMapping
	public String register(@RequestBody UserDTO userDTO) {
		UserModel userModel = new UserModel(userDTO);
		BeanUtils.copyProperties(userDTO, userModel);
		return userServices.register(userModel);
	}

	@GetMapping
	public List<FindUserDTO> findAll() {
		return userServices.findAll();
	}

}
