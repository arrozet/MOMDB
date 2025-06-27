package es.uma.taw.momdb.entity;

import es.uma.taw.momdb.dto.DTO;
import es.uma.taw.momdb.dto.ProductionCountryDTO;
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
@Table(name = "productioncountry")
public class Productioncountry implements Serializable, DTO<ProductionCountryDTO> {
    @Id
    @Column(name = "iso_3166_1", nullable = false, length = 5)
    private String iso31661;

    @Column(name = "country", nullable = false)
    private String country;

    @ManyToMany(mappedBy = "productioncountries")
    private Set<Movie> movies = new LinkedHashSet<>();

    public ProductionCountryDTO toDTO() {
        ProductionCountryDTO dto = new ProductionCountryDTO();
        dto.setIso31661(this.iso31661);
        dto.setCountry(this.country);
        return dto;
    }
}