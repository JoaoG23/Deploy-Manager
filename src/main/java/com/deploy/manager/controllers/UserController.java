package com.deploy.manager.controllers;


import com.deploy.manager.dtos.users.CreateUserDTO;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

	@PostMapping
	public void cadastrar(@RequestBody CreateUserDTO userDTO) {
		System.out.println(userDTO);
	}
}
