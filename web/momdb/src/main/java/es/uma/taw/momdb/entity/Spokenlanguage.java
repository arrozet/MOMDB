package es.uma.taw.momdb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "spokenlanguage")
public class Spokenlanguage {
    @Id
    @Column(name = "iso_639_1", nullable = false, length = 5)
    private String iso6391;

    @Column(name = "language", nullable = false)
    private String language;

}