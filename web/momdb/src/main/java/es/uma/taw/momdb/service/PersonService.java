package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.PersonRepository;
import es.uma.taw.momdb.dto.PersonDTO;
import es.uma.taw.momdb.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors -
 */

@Service
public class PersonService extends DTOService<PersonDTO, Person> {

    @Autowired
    private PersonRepository personRepository;

    public List<PersonDTO> findAll() {
        List<Person> people = this.personRepository.findAll();
        return this.entity2DTO(people);
    }
} 