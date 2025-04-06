package es.uma.taw.momdb.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CrewcharacterId implements java.io.Serializable {
    private static final long serialVersionUID = 9017326023440559233L;
    @Column(name = "crew_id", nullable = false)
    private Integer crewId;

    @Column(name = "character_id", nullable = false)
    private Integer characterId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CrewcharacterId entity = (CrewcharacterId) o;
        return Objects.equals(this.crewId, entity.crewId) &&
                Objects.equals(this.characterId, entity.characterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(crewId, characterId);
    }

}