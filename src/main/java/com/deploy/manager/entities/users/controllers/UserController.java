package com.deploy.manager.entities.users.controllers;


import com.deploy.manager.entities.users.dtos.UserDTO;
import com.deploy.manager.entities.users.dtos.UserViewedDTO;
import com.deploy.manager.entities.users.model.UserModel;
import com.deploy.manager.entities.users.services.UserServices;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserServices userServices;

	@PostMapping
	public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {

		UserModel userModel = new UserModel();
		BeanUtils.copyProperties(userDTO, userModel);

		userServices.register(userModel);
		return ResponseEntity.ok().body(" User created with success");
	}

	@GetMapping
	public ResponseEntity<List<UserViewedDTO>> findAll() {
		var users = userServices.findAll();
		return ResponseEntity.ok().body(users);
	}

	@GetMapping("page")
	public ResponseEntity<Page<UserViewedDTO>> getAllUsers(@PageableDefault(
			page = 0,
			size = 5,
			sort = "id",
			direction = Sort.Direction.ASC) Pageable pageable) {
		var pagesOfUsers = userServices.findAllByPage(pageable);
		return ResponseEntity.ok().body(pagesOfUsers);

	}


}
