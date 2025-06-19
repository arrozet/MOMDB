package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.CharacterRepository;
import es.uma.taw.momdb.dao.CrewRepository;
import es.uma.taw.momdb.dao.MovieRepository;
import es.uma.taw.momdb.dao.PersonRepository;
import es.uma.taw.momdb.dao.CrewRoleRepository;
import es.uma.taw.momdb.dto.CrewDTO;
import es.uma.taw.momdb.dto.MovieDTO;
import es.uma.taw.momdb.entity.Character;
import es.uma.taw.momdb.entity.Crew;
import es.uma.taw.momdb.entity.Movie;
import es.uma.taw.momdb.entity.Person;
import es.uma.taw.momdb.entity.Crewrole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors -
 */
@Slf4j
@Service
public class CrewService extends DTOService<CrewDTO, Crew>{

    @Autowired
    private CrewRepository crewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private CrewRoleRepository crewRoleRepository;

    @Autowired
    private MovieService movieService;

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

        //Actualizar la crew
        if(crew.getPerson().getId()!=crewDTO.getPersonaId()){
            Person nuevaPersona = this.personRepository.findById(crewDTO.getPersonaId()).orElse(null);

            List<Crew> crewsNuevoActor = this.crewRepository.findActorByPersonAndMovie(nuevaPersona.getId(), crew.getMovie().getId());
            Movie movie = movieRepository.findById(crew.getMovie().getId()).orElse(null);

            Character personaje = characterRepository.findById(crewDTO.getPersonajeId()).orElse(null);

            if(crew.getCharacters().size()==1){
                if(crewsNuevoActor.isEmpty()){ //Cambio a la persona (es lo comun)
                    crew.setPerson(nuevaPersona);
                    crewRepository.save(crew);
                }else{ //borro la crew de la persona y añado el personaje a la lista de la nueva persona
                    crewRepository.delete(crew);
                    Crew crewNuevoActor = crewsNuevoActor.get(0);
                    Set<Character> characters = crewNuevoActor.getCharacters();
                    characters.add(personaje);
                    crewNuevoActor.setCharacters(characters);
                    personaje.getCrews().add(crewNuevoActor);
                    characterRepository.save(personaje);
                    crewRepository.save(crewNuevoActor);
                }
            }else{
                crew.getCharacters().remove(personaje); //borro la crew de la persona
                crewRepository.save(crew);
                if(crewsNuevoActor.isEmpty()){ //Creo la crew de la nueva persona
                    personaje.getCrews().remove(crew);
                    characterRepository.save(personaje);
                    Crew nuevaCrew = new Crew();
                    nuevaCrew.setPerson(nuevaPersona);
                    nuevaCrew.setMovie(movie);
                    Set<Character> characters = new LinkedHashSet<>();
                    characters.add(personaje);
                    nuevaCrew.setCharacters(characters);
                    nuevaCrew.setCrewRole(crew.getCrewRole());
                    crewRepository.save(nuevaCrew);
                    movie.getCrews().remove(crew);
                    movie.getCrews().add(nuevaCrew);
                    movieRepository.save(movie);
                    personaje.getCrews().add(nuevaCrew);
                    characterRepository.save(personaje);
                }else{ //Añado a la otra persona
                    Crew crewNuevoActor = crewsNuevoActor.get(0);
                    Set<Character> characters = crewNuevoActor.getCharacters();
                    characters.add(personaje);
                    crewNuevoActor.setCharacters(characters);
                    personaje.getCrews().remove(crew);
                    personaje.getCrews().add(crewNuevoActor);
                    characterRepository.save(personaje);
                    crewRepository.save(crewNuevoActor);
                }
            }
        }
    }

    public void deleteCharacter(int characterId) {
        characterRepository.deleteById(characterId);
    }

    public void addNewCharacter(CrewDTO crewDTO) {
        Character character = new Character();
        character.setCharacter(crewDTO.getPersonajeName());
        characterRepository.save(character);

        Movie movie = movieRepository.findById(crewDTO.getPeliculaId()).orElse(null);
        Person nuevaPersona = this.personRepository.findById(crewDTO.getPersonaId()).orElse(null);
        List<Crew> crewsNuevoActor = this.crewRepository.findActorByPersonAndMovie(nuevaPersona.getId(), movie.getId());
        if(crewsNuevoActor.isEmpty()){ //Creo la crew de la nueva persona
            Crew nuevaCrew = new Crew();
            nuevaCrew.setPerson(nuevaPersona);
            nuevaCrew.setMovie(movie);
            Set<Character> characters = new LinkedHashSet<>();
            characters.add(character);
            nuevaCrew.setCharacters(characters);
            nuevaCrew.setCrewRole(crewRoleRepository.findAll().stream()
                    .filter(role -> role.getRole().equals("Actor"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No se encontró el rol de Actor")));
            crewRepository.save(nuevaCrew);
            movie.getCrews().add(nuevaCrew);
            movieRepository.save(movie);
            character.getCrews().add(nuevaCrew); // Actualizamos la relación bidireccional con el personaje
            characterRepository.save(character);
        }else{ //Añado a la otra persona
            Crew crewNuevoActor = crewsNuevoActor.get(0);
            Set<Character> characters = crewNuevoActor.getCharacters();
            characters.add(character);
            crewNuevoActor.setCharacters(characters);
            character.getCrews().add(crewNuevoActor);
            characterRepository.save(character);
            crewRepository.save(crewNuevoActor);
        }
    }

    public List<MovieDTO> findMoviesWherePersonIsActor(int personaId) {
        List<Crew> crews = crewRepository.findActorByPerson(personaId);
        List<Movie> movies = crews.stream().map(Crew::getMovie).distinct().toList();
        return movieService.entity2DTO(movies);
    }

    public List<MovieDTO> findMoviesWherePersonIsCrewNoActor(int personaId) {
        List<Crew> crews = crewRepository.findNonActorCrewByPerson(personaId);
        List<Movie> movies = crews.stream().map(Crew::getMovie).distinct().toList();
        return movieService.entity2DTO(movies);
    }

    public void deleteCrew(int crewId) {
        crewRepository.deleteById(crewId);
    }

    public List<Crewrole> findAllRolesExceptActor() {
        return crewRoleRepository.findAllExceptActor();
    }

    public boolean saveCrewEdit(CrewDTO crewDTO) {
        Crew crew = this.crewRepository.findById(crewDTO.getId()).orElse(null);

        if(crewDTO.getId()==-1){
            crew = new Crew();
            Movie movie = movieRepository.findById(crewDTO.getPeliculaId()).orElse(null);
            crew.setMovie(movie);
        }

        // Comprobar duplicado
        Crew existente = crewRepository.findByMovieAndPersonAndRole(crewDTO.getPeliculaId(), crewDTO.getPersonaId(), crewDTO.getRolId());
        if (existente != null && existente.getId() != crew.getId()) {
            return false;
        }

        Person nuevaPersona = this.personRepository.findById(crewDTO.getPersonaId()).orElse(null);
        Crewrole nuevoRol = crewRoleRepository.findById(crewDTO.getRolId()).orElse(null);
        crew.setPerson(nuevaPersona);
        crew.setCrewRole(nuevoRol);
        crewRepository.save(crew);
        return true;
    }

}
