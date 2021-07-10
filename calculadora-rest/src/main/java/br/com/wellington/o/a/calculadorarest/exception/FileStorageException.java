package br.com.wellington.o.a.calculadorarest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class FileStorageException extends RuntimeException {

	public FileStorageException(final String exception) {
		super(exception);
	}

	public FileStorageException(final String exception, Throwable cause) {
		super(exception, cause);
	}
}
