package com.deploy.manager.entities.users.authentication.controllers;


import com.deploy.manager.entities.users.authentication.dtos.UserAutheticationDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

	private AuthenticationManager manager;
	public ResponseEntity<?> toDologin(@Valid  UserAutheticationDto userAutheticationDto) {
//		var authentication = manager.authenticate(token)
	}

}
