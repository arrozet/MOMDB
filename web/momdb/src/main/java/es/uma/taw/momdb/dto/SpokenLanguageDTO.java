package es.uma.taw.momdb.dto;

import lombok.Data;

/*
 * @author arrozet (Rubén Oliva)
 */

@Data
public class SpokenLanguageDTO implements DTOWithNameAndId<String> {
    private String iso6391;
    private String language;

    public String getId() {
        return iso6391;
    }

    public String getName() {
        return language;
    }
} 