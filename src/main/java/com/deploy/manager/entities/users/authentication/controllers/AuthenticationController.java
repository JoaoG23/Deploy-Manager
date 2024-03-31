package com.deploy.manager.entities.users.authentication.controllers;


import com.deploy.manager.entities.users.authentication.dtos.UserAutheticationDto;
import com.deploy.manager.entities.users.model.UserModel;
import com.deploy.manager.infra.TokenServices.TokenServices;
import com.deploy.manager.infra.TokenServices.dtos.TokenDTO;
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

	@Autowired
	private TokenServices tokenServices; // Token


	@PostMapping
	public ResponseEntity<?> toDologin(@RequestBody @Valid UserAutheticationDto userAutheticationDto) {

		var authenticationToken = new UsernamePasswordAuthenticationToken(userAutheticationDto.getUsername(), userAutheticationDto.getPassword());
		var authentication = manager.authenticate(authenticationToken);

		var tokenSession = tokenServices.generateToken((UserModel) authentication.getPrincipal()); // Busca os dados usuario para o token

		var tokenJwtDTO = new TokenDTO();
		tokenJwtDTO.setToken(tokenSession);
		return ResponseEntity.ok().body(tokenJwtDTO);
	}
}
