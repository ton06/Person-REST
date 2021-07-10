package br.com.wellington.o.a.calculadorarest.data.vo;

import br.com.wellington.o.a.calculadorarest.data.model.Person;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonVO extends RepresentationModel<PersonVO> implements Serializable {

	private Long id;

	private String name;

	private String lastName;

	private String adress;

	private String gender;

	private Boolean enabled;

	public Person converterEmEntidade() {
		return DozerBeanMapperBuilder.buildDefault().map(this, Person.class);
	}
}
