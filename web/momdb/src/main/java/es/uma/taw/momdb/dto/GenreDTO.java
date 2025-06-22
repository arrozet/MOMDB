package es.uma.taw.momdb.dto;

import lombok.Data;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors -
 */

@Data
public class GenreDTO implements DTOWithNameAndId<Integer> {
    private Integer id;
    private String genero;

    public String getName() {
        return genero;
    }
}
