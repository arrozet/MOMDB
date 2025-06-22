package es.uma.taw.momdb.dto;

import lombok.Data;

/*
 * @author arrozet (Rubén Oliva)
 */

@Data
public class KeywordDTO implements DTOWithNameAndId<Integer> {
    private Integer id;
    private String keyword;

    public String getName() {
        return keyword;
    }
} 