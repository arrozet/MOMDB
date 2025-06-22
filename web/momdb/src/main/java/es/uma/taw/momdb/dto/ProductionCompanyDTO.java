package es.uma.taw.momdb.dto;

import lombok.Data;

/*
 * @author arrozet (Rubén Oliva)
 */

@Data
public class ProductionCompanyDTO implements DTOWithNameAndId<Integer> {
    private Integer id;
    private String name;
} 