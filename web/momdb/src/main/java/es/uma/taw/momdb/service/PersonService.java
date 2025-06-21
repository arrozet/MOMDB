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

/**
 * Servicio para gestionar la lógica de negocio de las operaciones sobre las personas.
 * Proporciona métodos para buscar, crear, actualizar y eliminar personas del sistema.
 * 
 * @author Artur797 (Artur Vargas)
 */

@Service
public class PersonService extends DTOService<PersonDTO, Person> {

    @Autowired
    private PersonRepository personRepository;

    /**
     * Obtiene todas las personas de la base de datos.
     * @return Lista de DTO de personas.
     */
    public List<PersonDTO> findAll() {
        List<Person> people = this.personRepository.findAll();
        return this.entity2DTO(people);
    }

    /**
     * Elimina una persona por su ID.
     * @param id El ID de la persona a eliminar.
     */
    public void deleteById(Integer id) {
        this.personRepository.deleteById(id);
    }

    /**
     * Obtiene las primeras 1000 personas de la base de datos.
     * @return Lista de DTO de personas.
     */
    public List<PersonDTO> findFirst1000() {
        List<Person> people = this.personRepository.findAll(PageRequest.of(0, 1000)).getContent();
        return this.entity2DTO(people);
    }

    /**
     * Busca personas por su nombre.
     * @param name El nombre a buscar.
     * @return Lista de DTO de personas que coinciden con el nombre.
     */
    public List<PersonDTO> searchByName(String name) {
        List<Person> people = this.personRepository.findByNameContainingIgnoreCase(name);
        return this.entity2DTO(people);
    }

    /**
     * Guarda o actualiza una persona.
     * Si el ID del DTO es -1, crea una nueva persona.
     * @param personDTO DTO con la información de la persona.
     */
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

    /**
     * Busca una persona por su ID.
     * @param id El ID de la persona.
     * @return El DTO de la persona, o null si no se encuentra.
     */
    public PersonDTO findById(Integer id) {
        Person person = this.personRepository.findById(id).orElse(null);
        return person != null ? person.toDTO() : null;
    }

    /**
     * Busca personas de forma paginada y, opcionalmente, filtrada por nombre.
     * @param name Nombre por el que filtrar (puede ser nulo o vacío).
     * @param page Número de página.
     * @param size Tamaño de la página.
     * @return Página de DTO de personas.
     */
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