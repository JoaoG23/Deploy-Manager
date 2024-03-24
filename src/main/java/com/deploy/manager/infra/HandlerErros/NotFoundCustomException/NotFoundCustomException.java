package com.deploy.manager.infra.HandlerErros.NotFoundCustomException;

public class NotFoundCustomException extends RuntimeException {
	public NotFoundCustomException(String msg) {
		super(msg);
	}
}