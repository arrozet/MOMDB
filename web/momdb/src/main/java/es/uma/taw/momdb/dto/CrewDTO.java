package es.uma.taw.momdb.dto;

import lombok.Data;

import java.util.List;

/*
 * @author projectGeorge (Jorge Repullo - 63.6%), Artur797 (Artur Vargas - 36.4%)
 */
@Data
public class CrewDTO {
    private int id = -1;
    private String persona;
    private int personaId;
    private int peliculaId;
    private String rol;
    private List<CharacterDTO> personajes;
    private int personajeId; //Del que se actualiza
    private String personajeName; //Del que se añade
    private int rolId; 
}
