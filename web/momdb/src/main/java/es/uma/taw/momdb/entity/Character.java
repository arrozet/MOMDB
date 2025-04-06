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
@Table(name = "`character`")
public class Character {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "`character`", nullable = false)
    private String character;

}