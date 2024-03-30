package com.deploy.manager.infra.TokenServices;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.deploy.manager.entities.users.model.UserModel;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServices {
	public String generateToken (UserModel userModel) {
		try {
			Algorithm algorithm = Algorithm.HMAC256("joao");
			return JWT.create()
					.withIssuer("deploys_manager_api")
					.withSubject(userModel.getUsername()) // Adiciona uma string ao token
					.withExpiresAt(getDateExpiry())
					.sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Erro to generate token", exception);
		}
	}
	private Instant getDateExpiry() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
