package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.PersonRepository;
import es.uma.taw.momdb.dto.PersonDTO;
import es.uma.taw.momdb.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
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

    public void deleteById(Integer id) {
        this.personRepository.deleteById(id);
    }

    public List<PersonDTO> findFirst1000() {
        List<Person> people = this.personRepository.findAll(PageRequest.of(0, 1000)).getContent();
        return this.entity2DTO(people);
    }

    public List<PersonDTO> searchByName(String name) {
        List<Person> people = this.personRepository.findByNameContainingIgnoreCase(name);
        return this.entity2DTO(people);
    }

    public void save(PersonDTO personDTO) {
        Person person;
        if(personDTO.getId() == -1) {
            person= new Person();
        }else{
            person = this.personRepository.findById(personDTO.getId()).get();
        }
        person.setName(personDTO.getName());
        this.personRepository.save(person);
    }

    public PersonDTO findById(Integer id) {
        Person person = this.personRepository.findById(id).orElse(null);
        return person != null ? person.toDTO() : null;
    }

    public Page<PersonDTO> findPeoplePaged(String name, int page, int size) {
        Page<Person> peoplePage;
        if (name != null && !name.trim().isEmpty()) {
            peoplePage = this.personRepository.findByNameContainingIgnoreCase(name, PageRequest.of(page, size));
        } else {
            peoplePage = this.personRepository.findAll(PageRequest.of(page, size));
        }
        // Convertimos la página de entidades a DTOs
        List<PersonDTO> dtos = this.entity2DTO(peoplePage.getContent());
        // Devolvemos una página de DTOs (creamos una nueva PageImpl)
        return new PageImpl<>(dtos, peoplePage.getPageable(), peoplePage.getTotalElements());
    }
} 