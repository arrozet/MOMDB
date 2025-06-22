package es.uma.taw.momdb.entity;

import es.uma.taw.momdb.dto.DTO;
import es.uma.taw.momdb.dto.SpokenLanguageDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "spokenlanguage")
public class Spokenlanguage implements Serializable, DTO<SpokenLanguageDTO> {
    @Id
    @Column(name = "iso_639_1", nullable = false, length = 5)
    private String iso6391;

    @Column(name = "language", nullable = false)
    private String language;

    @ManyToMany(mappedBy = "spokenlanguages")
    private Set<Movie> movies = new LinkedHashSet<>();

    public SpokenLanguageDTO toDTO() {
        SpokenLanguageDTO dto = new SpokenLanguageDTO();
        dto.setIso6391(this.iso6391);
        dto.setLanguage(this.language);
        return dto;
    }
}