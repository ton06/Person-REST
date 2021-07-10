package br.com.wellington.o.a.calculadorarest.reposiroty;

import br.com.wellington.o.a.calculadorarest.data.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	@Modifying
	@Query("update Person p set p.enabled = 'N' where p.id = :id")
	void disablePerson(@Param("id") Long id);

	@Query("select p from Person p where p.name like :name")
	Page<Person> findPersonByName(@Param("name") String name, Pageable pageable);
}
