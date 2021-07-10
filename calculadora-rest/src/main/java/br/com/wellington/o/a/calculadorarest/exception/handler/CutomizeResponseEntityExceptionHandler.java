package br.com.wellington.o.a.calculadorarest.exception.handler;

import br.com.wellington.o.a.calculadorarest.exception.ExceptionResponse;
import br.com.wellington.o.a.calculadorarest.exception.InvalidJwtAuthException;
import br.com.wellington.o.a.calculadorarest.exception.OpeacaoNaoSuportadaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CutomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handler(Exception e, WebRequest webRequest) {
		final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), e.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(OpeacaoNaoSuportadaException.class)
	public final ResponseEntity<ExceptionResponse> handlerBadResquet(Exception e, WebRequest webRequest) {
		final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), e.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(InvalidJwtAuthException.class)
	public final ResponseEntity<ExceptionResponse> invalidJwtAuthException(Exception e, WebRequest webRequest) {
		final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), e.getMessage(), webRequest.getDescription(false));
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}
}
