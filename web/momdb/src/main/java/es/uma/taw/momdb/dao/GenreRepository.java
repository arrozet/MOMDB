package es.uma.taw.momdb.dao;

import es.uma.taw.momdb.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
