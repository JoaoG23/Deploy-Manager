package com.deploy.manager.entities.users.authentication.controllers;


import com.deploy.manager.entities.users.authentication.dtos.UserAutheticationDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager manager; // Ao Chamar essa classe o spring chamará a classe serviceAuthentication
	@PostMapping
	public ResponseEntity<?> toDologin(@RequestBody @Valid UserAutheticationDto userAutheticationDto) {

		var token = new UsernamePasswordAuthenticationToken(userAutheticationDto.getUsername(), userAutheticationDto.getPassword());
		var authentication = manager.authenticate(token);
		return ResponseEntity.ok().build();
	}
}
