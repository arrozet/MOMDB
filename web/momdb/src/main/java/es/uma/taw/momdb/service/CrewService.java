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

/**
 * Servicio para gestionar la lógica de negocio de las operaciones sobre los miembros del equipo (Crew).
 * Proporciona métodos para buscar, actualizar y eliminar miembros del equipo, así como gestionar
 * la relación entre actores, personajes y películas.
 * 
 * @author Artur797 (Artur Vargas)
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
    @Autowired
    private CharacterService characterService;

    /**
     * Devuelve una lista de todos los actores.
     * @return Lista de DTO de miembros del equipo que son actores.
     */
    public List<CrewDTO> listarActores () {
        return this.listarActores(null);
    }

    /**
     * Devuelve una lista de actores filtrada por nombre.
     * Si no se proporciona un nombre, devuelve todos los actores.
     * @param name Nombre por el que filtrar.
     * @return Lista de DTO de miembros del equipo que son actores.
     */
    public List<CrewDTO> listarActores (String name) {
        List<Crew> crew;

        if ((name == null || name.isEmpty())) {
            crew = crewRepository.filterActors();
        } else { // solo título
            crew = this.crewRepository.filterByName(name);
        }
        return this.entity2DTO(crew);
    }

    /**
     * Busca un miembro del equipo por su ID.
     * @param id El ID del miembro del equipo.
     * @return El DTO del miembro del equipo, o null si no se encuentra.
     */
    public CrewDTO findCrewById (int id) {
        Crew crew = this.crewRepository.findById(id).orElse(null);
        return crew != null ? crew.toDTO() : null;
    }

    /**
     * Guarda los cambios de un miembro del equipo (actor).
     * Gestiona la lógica de cambiar la persona asociada a un personaje en una película.
     * @param crewDTO DTO con la información a actualizar.
     */
    public void saveCrew(CrewDTO crewDTO) {
        Crew crew = this.crewRepository.findById(crewDTO.getId()).orElse(null);

        Character personaje = characterRepository.findById(crewDTO.getPersonajeId()).orElse(null);

        characterService.updateName(personaje, crewDTO.getPersonajeName());

        //Actualizar la crew
        if(crew.getPerson().getId()!=crewDTO.getPersonaId()){
            Person nuevaPersona = this.personRepository.findById(crewDTO.getPersonaId()).orElse(null);

            List<Crew> crewsNuevoActor = this.crewRepository.findActorByPersonAndMovie(nuevaPersona.getId(), crew.getMovie().getId());
            Movie movie = movieRepository.findById(crew.getMovie().getId()).orElse(null);


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
                    characterService.addCrew(personaje,crewNuevoActor);
                    crewRepository.save(crewNuevoActor);
                }
            }else{
                manageActorCrewAssignment(crewsNuevoActor, nuevaPersona, movie, personaje, crew);
            }
        }
    }


    /**
     * Añade un nuevo personaje y lo asigna a un actor en una película.
     * Si el actor no tiene un rol en esa película, se crea.
     * @param crewDTO DTO con la información del nuevo personaje y su asignación.
     */
    public void addNewCharacter(CrewDTO crewDTO) {
        Character character = characterService.createCharacter(crewDTO.getPersonajeName());

        Movie movie = movieRepository.findById(crewDTO.getPeliculaId()).orElse(null);
        Person nuevaPersona = this.personRepository.findById(crewDTO.getPersonaId()).orElse(null);
        List<Crew> crewsNuevoActor = this.crewRepository.findActorByPersonAndMovie(nuevaPersona.getId(), movie.getId());
        manageActorCrewAssignment(crewsNuevoActor, nuevaPersona, movie, character, null);
    }

    /**
     * Gestiona la asignación de un personaje a un actor en una película.
     * Si el actor no tiene un registro de Crew en esa película, se crea uno nuevo.
     * Si ya existe, se añade el personaje a la lista de personajes del Crew existente.
     * Además, actualiza las relaciones entre personaje y Crew, y entre película y Crew.
     *
     * @param crewsNuevoActor Lista de Crew existentes para la persona y película dadas.
     * @param nuevaPersona    La persona (actor) a la que se va a asignar el personaje.
     * @param movie           La película en la que se realiza la asignación.
     * @param personaje       El personaje que se va a asignar.
     * @param crew            El Crew original (puede ser null si se está creando uno nuevo).
     */
    private void manageActorCrewAssignment(List<Crew> crewsNuevoActor, Person nuevaPersona, Movie movie, Character personaje, Crew crew) {
        Crew nuevaCrew;
        if(crewsNuevoActor.isEmpty()){ //Creo la crew de la nueva persona
            nuevaCrew = new Crew();
            nuevaCrew.setPerson(nuevaPersona);
            nuevaCrew.setMovie(movie);
            Set<Character> characters = new LinkedHashSet<>();
            characters.add(personaje);
            nuevaCrew.setCharacters(characters);
            nuevaCrew.setCrewRole(crewRoleRepository.findActor());
            movieService.removeAndAddCrew(movie, crew,nuevaCrew);
        }else{ //Añado a la otra persona
            nuevaCrew = crewsNuevoActor.get(0);
            Set<Character> characters = nuevaCrew.getCharacters();
            characters.add(personaje);
            nuevaCrew.setCharacters(characters);
        }
        crewRepository.save(nuevaCrew);
        characterService.removeAndAddCrew(personaje, crew, nuevaCrew);
    }

    /**
     * Elimina la asociación de un personaje a un miembro del equipo (actor).
     * Si el personaje no queda asignado a ningún otro actor, se elimina.
     * @param crew DTO del miembro del equipo.
     * @param characterId ID del personaje a desasignar.
     */
    public void deleteCrewCharacter(CrewDTO crew,int characterId) {
        Crew crewEntity = crewRepository.findById(crew.getId()).get();
        Character character = characterRepository.findById(characterId).get();
        crewEntity.getCharacters().remove(character);
        if(character.getCrews().isEmpty()){
            characterService.deleteCharacter(characterId);
        }else{
            characterService.removeCrew(character, crewEntity);
        }
        crewRepository.save(crewEntity);
    }

    /**
     * Busca las películas en las que una persona ha trabajado como actor.
     * @param personaId ID de la persona.
     * @return Lista de DTO de las películas.
     */
    public List<MovieDTO> findMoviesWherePersonIsActor(int personaId) {
        List<Crew> crews = crewRepository.findActorByPerson(personaId);
        List<Movie> movies = crews.stream().map(Crew::getMovie).distinct().toList();
        return movieService.entity2DTO(movies);
    }

    /**
     * Busca las películas en las que una persona ha trabajado en roles que no son de actor.
     * @param personaId ID de la persona.
     * @return Lista de DTO de las películas.
     */
    public List<MovieDTO> findMoviesWherePersonIsCrewNoActor(int personaId) {
        List<Crew> crews = crewRepository.findNonActorCrewByPerson(personaId);
        List<Movie> movies = crews.stream().map(Crew::getMovie).distinct().toList();
        return movieService.entity2DTO(movies);
    }

    /**
     * Elimina un miembro del equipo por su ID.
     * @param crewId ID del miembro del equipo a eliminar.
     */
    public void deleteCrew(int crewId) {
        crewRepository.deleteById(crewId);
    }


    /**
     * Guarda los cambios de un miembro del equipo (no actor).
     * Puede crear un nuevo miembro o actualizar uno existente.
     * @param crewDTO DTO con la información a guardar.
     * @return `true` si se guardó correctamente, `false` si ya existe un miembro con la misma persona, película y rol.
     */
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
