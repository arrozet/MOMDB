package es.uma.taw.momdb.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MovieDTO {
    private Integer id = -1;
    private String titulo;
    private String idiomaOriginal;
    private LocalDate fechaDeSalida;
    private Long Ingresos;
    private List<GenreDTO> generos;
}
