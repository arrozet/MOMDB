package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.CharacterRepository;
import es.uma.taw.momdb.dao.CrewRepository;
import es.uma.taw.momdb.dao.PersonRepository;
import es.uma.taw.momdb.dto.CrewDTO;
import es.uma.taw.momdb.entity.Character;
import es.uma.taw.momdb.entity.Crew;
import es.uma.taw.momdb.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors -
 */

@Service
public class CrewService extends DTOService<CrewDTO, Crew>{

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private PersonRepository personRepository;

    public List<CrewDTO> listarActores () {
        return this.listarActores(null);
    }

    public List<CrewDTO> listarActores (String name) {
        List<Crew> crew;

        if ((name == null || name.isEmpty())) {
            crew = crewRepository.filterActors();
        } else { // solo título
            crew = this.crewRepository.filterByName(name);
        }
        return this.entity2DTO(crew);
    }

    public CrewDTO findCrewById (int id) {
        Crew crew = this.crewRepository.findById(id).orElse(null);
        return crew != null ? crew.toDTO() : null;
    }

    public void saveCrew(CrewDTO crewDTO) {
        Crew crew = this.crewRepository.findById(crewDTO.getId()).orElse(null);
        if (crew != null) {
            // Actualizar la persona en la tabla Crew
            Person person = this.personRepository.findById(crewDTO.getPersonaId()).orElse(null);
            if (person != null) {
                crew.setPerson(person);
            }
            
            // Actualizar los nombres de los personajes
            int index = 0;
            for (Character character : crew.getCharacters()) {
                if (index < crewDTO.getPersonajes().size()) {
                    character.setCharacter(crewDTO.getPersonajes().get(index).getCharacterName());
                    this.characterRepository.save(character);
                    index++;
                }
            }
            
            this.crewRepository.save(crew);
        }
    }

}
