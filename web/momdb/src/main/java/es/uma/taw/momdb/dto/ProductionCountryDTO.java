package es.uma.taw.momdb.dto;

import lombok.Data;

/*
 * @author arrozet (Rubén Oliva)
 */

@Data
public class ProductionCountryDTO implements DTOWithNameAndId<String> {
    private String iso31661;
    private String country;

    public String getId() {
        return iso31661;
    }

    public String getName() {
        return country;
    }
} 