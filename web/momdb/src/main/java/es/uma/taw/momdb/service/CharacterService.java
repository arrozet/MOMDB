package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.CharacterRepository;
import es.uma.taw.momdb.dto.CharacterDTO;

import es.uma.taw.momdb.entity.Character;
import es.uma.taw.momdb.entity.Crew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servicio para gestionar las operaciones de personajes.
 * Proporciona métodos para interactuar con las entidades y usuarios del sistema.
 * 
 * @author Artur797 (Artur Vargas - 55.3%), arrozet (Rubén Oliva, Javadocs - 44.7%)
 */

@Service
public class CharacterService extends DTOService<CharacterDTO, Character>{
    @Autowired
    private CharacterRepository characterRepository;

    /**
     * Busca un personaje por su ID.
     * @param id El ID del personaje.
     * @return El DTO del personaje, o null si no se encuentra.
     */
    public CharacterDTO findById (int id) {
        Character character = this.characterRepository.findById(id).orElse(null);
        return character != null ? character.toDTO() : null;
    }

    /**
     * Elimina un personaje.
     * @param characterId El ID del personaje a eliminar.
     */
    public void deleteCharacter(int characterId) {
        characterRepository.deleteById(characterId);
    }

    /**
     * Desasigna un miembro del equipo de un personaje.
     * @param character El personaje.
     * @param crew El miembro del equipo a desasignar.
     */
    public void removeCrew(Character character, Crew crew) {
        character.getCrews().remove(crew);
        characterRepository.save(character);
    }

    /**
     * Asigna un miembro del equipo a un personaje.
     * @param character El personaje.
     * @param crew El miembro del equipo a asignar.
     */
    public void addCrew(Character character, Crew crew) {
        character.getCrews().add(crew);
        characterRepository.save(character);
    }

    /**
     * Reemplaza un miembro del equipo asignado a un personaje por otro.
     * @param character El personaje.
     * @param crewAnt El miembro del equipo antiguo.
     * @param crewNueva El miembro del equipo nuevo.
     */
    public void removeAndAddCrew(Character character, Crew crewAnt, Crew crewNueva) {
        character.getCrews().remove(crewAnt);
        character.getCrews().add(crewNueva);
        characterRepository.save(character);
    }

    /**
     * Crea un nuevo personaje.
     * @param name El nombre del personaje.
     * @return La entidad del personaje creado.
     */
    public Character createCharacter(String name) {
        Character character = new Character();
        character.setCharacter(name);
        characterRepository.save(character);
        return character;
    }

    /**
     * Actualiza el nombre de un personaje.
     * @param character El personaje a actualizar.
     * @param name El nuevo nombre.
     */
    public void updateName(Character character, String name) {
        character.setCharacter(name);
        characterRepository.save(character);
    }
}
