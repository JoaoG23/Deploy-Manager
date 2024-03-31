package com.deploy.manager.infra.TokenServices;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.deploy.manager.entities.users.model.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenServices {

	@Value("${api.security.token.secret}")
	private String secret;
	public String generateToken (UserModel userModel) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.create()
					.withIssuer("deploys_manager_api")
					.withSubject(userModel.getUsername()) // Adiciona uma string ao token
					.withExpiresAt(getDateExpiry())
					.sign(algorithm);
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Erro to generate token", exception);
		}
	}

	public String getSubject(String tokenJwt) {
		try {

			// Valida Token Jwt
			Algorithm algorithm = Algorithm.HMAC256(secret);

			return JWT.require(algorithm)
					.withIssuer("deploys_manager_api")
					.build()
					.verify(tokenJwt)
					.getSubject();

		} catch (JWTVerificationException exception){
			throw new RuntimeException("Token invalid or expired!", exception);
		}
	}

	private Instant getDateExpiry() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
