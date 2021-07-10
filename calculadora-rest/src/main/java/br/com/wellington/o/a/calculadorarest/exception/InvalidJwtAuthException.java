package br.com.wellington.o.a.calculadorarest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidJwtAuthException extends AuthenticationException {

	public InvalidJwtAuthException(final String exception) {
		super(exception);
	}
}
