package es.uma.taw.momdb.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors -
 */
@Data
public class MovieDTO {
    private Integer id = -1;
    private String titulo;
    private String idiomaOriginal;
    private LocalDate fechaDeSalida;
    private Long ingresos;
    private List<GenreDTO> generos;
    private List<Integer> generoIds;
    private BigDecimal mediaVotos;
    private Integer votos;
    private String descripcion;
    private String imageLink;
    private String tagline;
    private List<CrewDTO> equipo;
    private List<Integer> equipoIds;
}
