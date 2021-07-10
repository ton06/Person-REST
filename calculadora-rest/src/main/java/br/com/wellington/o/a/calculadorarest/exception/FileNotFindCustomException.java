package br.com.wellington.o.a.calculadorarest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileNotFindCustomException extends RuntimeException {

	public FileNotFindCustomException(final String exception) {
		super(exception);
	}

	public FileNotFindCustomException(final String exception, Throwable cause) {
		super(exception, cause);
	}
}
