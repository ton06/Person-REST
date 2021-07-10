package br.com.wellington.o.a.calculadorarest.services;

import br.com.wellington.o.a.calculadorarest.data.model.Person;
import br.com.wellington.o.a.calculadorarest.data.vo.PersonVO;
import br.com.wellington.o.a.calculadorarest.reposiroty.PersonRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class PersonService {

	private final PersonRepository personRepository;

	@Autowired
	public PersonService(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public PersonVO findById(String id) throws NotFoundException {
		Optional<Person> person = personRepository.findById(Long.parseLong(id));
		if (!person.isPresent()) {
			throw new NotFoundException("Não encontrado");
		}
		return person.get().converterEmVo();
	}

	public Page<PersonVO> findAll(Pageable pageable) {

		var page = personRepository.findAll(pageable);

		return page.map(this::convertToPersonVo);
	}

	public Page<PersonVO> findPersonByName(String name, Pageable pageable) {
		return personRepository.findPersonByName(String.format("%s%%", name).toLowerCase(Locale.ROOT), pageable).map(this::convertToPersonVo);
	}

	private PersonVO convertToPersonVo(Person person) {
		return person.converterEmVo();
	}

	public PersonVO create(PersonVO person) {
		return personRepository.save(person.converterEmEntidade()).converterEmVo();
	}

	public PersonVO update(PersonVO person) throws NotFoundException {
		PersonVO banco = findById(person.getId().toString());
		banco.setName(person.getName());
		banco.setLastName(person.getLastName());
		banco.setAdress(person.getAdress());
		banco.setGender(person.getGender());
		return personRepository.save(banco.converterEmEntidade()).converterEmVo();
	}

	@Transactional
	public PersonVO disablePerson(Long id) throws NotFoundException {
		personRepository.disablePerson(id);
		return findById(id.toString());
	}

	public void delete(String id) throws NotFoundException {
		PersonVO person = findById(id);
		personRepository.delete(person.converterEmEntidade());
	}
}
