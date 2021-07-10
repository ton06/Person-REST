package br.com.wellington.o.a.calculadorarest.converter;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;


public class Converter extends AbstractJackson2HttpMessageConverter {

	public Converter() {
		super(new YAMLMapper(), MediaType.parseMediaType("application/x-yaml"));
	}
}
