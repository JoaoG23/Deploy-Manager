package com.deploy.manager.infra.HandlerErros;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerErros {

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handle404() {
		return  ResponseEntity.notFound().build();
	}
}
