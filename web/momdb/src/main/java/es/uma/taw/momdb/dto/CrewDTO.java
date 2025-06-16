package es.uma.taw.momdb.dto;

import lombok.Data;

import java.util.List;

@Data
public class CrewDTO {
    private int id = -1;
    private String persona;
    private int peliculaId;
    private String rol;
    private List<CharacterDTO> personajes;
}
