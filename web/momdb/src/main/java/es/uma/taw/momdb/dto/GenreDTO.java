package es.uma.taw.momdb.dto;

import lombok.Data;

/*
 * @author arrozet (Rubén Oliva - 55.6%), Artur797 (Artur Vargas - 44.4%)
 */
@Data
public class GenreDTO implements DTOWithNameAndId<Integer> {
    private Integer id;
    private String genero;

    public String getName() {
        return genero;
    }
}
