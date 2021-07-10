package br.com.wellington.o.a.calculadorarest.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse implements Serializable {

	private Date timestamp;
	private String message;
	private String details;


}
