package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.GenreRepository;
import es.uma.taw.momdb.dto.GenreDTO;
import es.uma.taw.momdb.entity.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors -
 */

@Service
public class GeneroService extends DTOService<GenreDTO, Genre>{

    @Autowired
    private GenreRepository genreRepository;

    public List<GenreDTO> listarGeneros () {
        List<Genre> entities = this.genreRepository.findAll();
        return this.entity2DTO(entities);
    }

}
