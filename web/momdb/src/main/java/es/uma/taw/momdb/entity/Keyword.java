package es.uma.taw.momdb.entity;

import es.uma.taw.momdb.dto.DTO;
import es.uma.taw.momdb.dto.KeywordDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

/*
 * @author arrozet (Rubén Oliva)
 */

@Getter
@Setter
@Entity
@Table(name = "keywords")
public class Keyword implements Serializable, DTO<KeywordDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "keyword", nullable = false)
    private String keyword;

    @ManyToMany
    @JoinTable(name = "movie_keywords",
            joinColumns = @JoinColumn(name = "keywords_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<Movie> movies = new LinkedHashSet<>();

    public KeywordDTO toDTO() {
        KeywordDTO dto = new KeywordDTO();
        dto.setId(this.id);
        dto.setKeyword(this.keyword);
        return dto;
    }
}