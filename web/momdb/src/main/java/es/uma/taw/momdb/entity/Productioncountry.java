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
@Table(name = "productioncountry")
public class Productioncountry {
    @Id
    @Column(name = "iso_3166_1", nullable = false, length = 5)
    private String iso31661;

    @Column(name = "country", nullable = false)
    private String country;

}