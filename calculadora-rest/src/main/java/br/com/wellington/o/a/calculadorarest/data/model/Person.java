package br.com.wellington.o.a.calculadorarest.data.model;

import br.com.wellington.o.a.calculadorarest.converter.BooleanToStringConverter;
import br.com.wellington.o.a.calculadorarest.data.vo.PersonVO;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "Person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Person implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "lastName")
	private String lastName;

	@Column(name = "adress")
	private String adress;

	@Column(name = "gender")
	private String gender;

	@Convert(converter = BooleanToStringConverter.class)
	@Column(name = "enabled")
	private Boolean enabled;

	public PersonVO converterEmVo() {
		return DozerBeanMapperBuilder.buildDefault().map(this, PersonVO.class);
	}
}
