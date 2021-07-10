package br.com.wellington.o.a.calculadorarest.controlador;

import br.com.wellington.o.a.calculadorarest.data.vo.PersonVO;
import br.com.wellington.o.a.calculadorarest.services.PersonService;
import io.swagger.annotations.Api;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Api(value = "Person Endpoint", tags = {"Person", "PersonVO", "CRUD"})
@CrossOrigin(origins = {"http://localhost:8080"})
@RestController
@RequestMapping("api/person/v1")
public class PersonController {

	private PersonService personService;
	private final PagedResourcesAssembler<PersonVO> assembler;

	@Autowired
	public PersonController(PersonService personService, PagedResourcesAssembler<PersonVO> assembler) {
		this.personService = personService;
		this.assembler = assembler;
	}

	@CrossOrigin(origins = {"http://localhost:8080", "www.google.com"})
	@GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
	public PersonVO findById(@PathVariable("id") String id) throws NotFoundException {
		PersonVO personVO = personService.findById(id);
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personVO;
	}

	@CrossOrigin(origins = {"http://localhost:8080", "www.google.com"})
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
	public ResponseEntity<PagedResourcesAssembler<PersonVO>> findByAll(@RequestParam(value = "page", defaultValue = "0") int page,
																	   @RequestParam(value = "limit", defaultValue = "12") int limit,
																	   @RequestParam(value = "direction", defaultValue = "asc") String direction
	) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));

		Page<PersonVO> personVOList = personService.findAll(pageable);
		personVOList.forEach(personVO -> {
			try {
				personVO.add(linkTo(methodOn(PersonController.class)
						.findById(personVO.getId().toString())).withSelfRel());
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		});
		return new ResponseEntity(assembler.toModel(personVOList), HttpStatus.OK);
	}

	@CrossOrigin(origins = {"http://localhost:8080", "www.google.com"})
	@GetMapping(value = "/findPersonByName/{name}",
			produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
	public ResponseEntity<PagedResourcesAssembler<PersonVO>> findPersonByName(
			@PathVariable("name") String name,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "limit", defaultValue = "12") int limit,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {

		var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection, "name"));

		Page<PersonVO> personVOList = personService.findPersonByName(name, pageable);
		personVOList.forEach(personVO -> {
			try {
				personVO.add(linkTo(methodOn(PersonController.class)
						.findById(personVO.getId().toString())).withSelfRel());
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
		});
		return new ResponseEntity(assembler.toModel(personVOList), HttpStatus.OK);
	}

	@PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"},
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
	public PersonVO create(@RequestBody PersonVO person) throws NotFoundException {
		PersonVO personVO = personService.create(person);
		personVO.add(linkTo(methodOn(PersonController.class)
				.findById(personVO.getId().toString())).withSelfRel());
		return personVO;
	}

	@PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"},
			consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
	public PersonVO update(@RequestBody PersonVO person) throws NotFoundException {
		PersonVO retorno = personService.update(person);
		retorno.add(linkTo(methodOn(PersonController.class)
				.findById(retorno.getId().toString())).withSelfRel());
		return retorno;
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id) throws NotFoundException {
		personService.delete(id);
		return ResponseEntity.ok().build();
	}

	@CrossOrigin(origins = {"http://localhost:8080", "www.google.com"})
	@PatchMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "application/x-yaml"})
	public PersonVO findById(@PathVariable("id") Long id) throws NotFoundException {
		PersonVO personVO = personService.disablePerson(id);
		personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personVO;
	}
}
