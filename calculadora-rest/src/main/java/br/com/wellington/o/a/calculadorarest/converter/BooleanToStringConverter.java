package br.com.wellington.o.a.calculadorarest.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {


	@Override
	public String convertToDatabaseColumn(Boolean aBoolean) {
		return aBoolean != null && aBoolean ? "S" : "N";
	}

	@Override
	public Boolean convertToEntityAttribute(String s) {
		return "S".equals(s);
	}
}
