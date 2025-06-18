package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.CharacterRepository;
import es.uma.taw.momdb.dto.CharacterDTO;

import es.uma.taw.momdb.entity.Character;
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
}
