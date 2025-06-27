package es.uma.taw.momdb.entity;

import es.uma.taw.momdb.dto.DTO;
import es.uma.taw.momdb.dto.ProductionCompanyDTO;
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
@Table(name = "productioncompany")
public class Productioncompany implements Serializable, DTO<ProductionCompanyDTO> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "company", nullable = false)
    private String company;

    @ManyToMany(mappedBy = "productioncompanies")
    private Set<Movie> movies = new LinkedHashSet<>();

    public ProductionCompanyDTO toDTO() {
        ProductionCompanyDTO dto = new ProductionCompanyDTO();
        dto.setId(this.id);
        dto.setName(this.company);
        return dto;
    }
}