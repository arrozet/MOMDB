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
@Table(name = "productioncompany")
public class Productioncompany {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "company", nullable = false)
    private String company;

}