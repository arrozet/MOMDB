package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.CharacterRepository;
import es.uma.taw.momdb.dto.CharacterDTO;

import es.uma.taw.momdb.entity.Character;
import es.uma.taw.momdb.entity.Crew;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacterService extends DTOService<CharacterDTO, Character>{
    @Autowired
    private CharacterRepository characterRepository;

    public CharacterDTO findById (int id) {
        Character character = this.characterRepository.findById(id).orElse(null);
        return character != null ? character.toDTO() : null;
    }

    public void deleteCharacter(int characterId) {
        characterRepository.deleteById(characterId);
    }

    public void removeCrew(Character character, Crew crew) {
        character.getCrews().remove(crew);
        characterRepository.save(character);
    }

    public void addCrew(Character character, Crew crew) {
        character.getCrews().add(crew);
        characterRepository.save(character);
    }

    public void removeAndAddCrew(Character character, Crew crewAnt, Crew crewNueva) {
        character.getCrews().remove(crewAnt);
        character.getCrews().add(crewNueva);
        characterRepository.save(character);
    }

    public Character createCharacter(String name) {
        Character character = new Character();
        character.setCharacter(name);
        characterRepository.save(character);
        return character;
    }
}
