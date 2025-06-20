package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dao.GenreRepository;
import es.uma.taw.momdb.dto.GenreDTO;
import es.uma.taw.momdb.entity.Genre;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors - arrozet (Rubén Oliva)
 */

@Service
public class GeneroService extends DTOService<GenreDTO, Genre>{

    @Autowired
    private GenreRepository genreRepository;

    public List<GenreDTO> listarGeneros () {
        List<Genre> entities = this.genreRepository.findAll();
        return this.entity2DTO(entities);
    }

    public Genre findGenre(int id) {
        return genreRepository.findById(id).orElse(null);
    }

    public void deleteGenre(int id) {
        genreRepository.deleteById(id);
    }

    public void updateGenre(int id, String name) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Genre not found with id: " + id));
        genre.setGenre(name);
        genreRepository.save(genre);
    }
}
