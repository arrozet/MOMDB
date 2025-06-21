package es.uma.taw.momdb.ui;

import lombok.Data;

import java.math.BigDecimal;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors - projectGeorge (Jorge Repullo)
 */

@Data
public class Filtro {
    String texto;
    Integer generoId;
    Integer year;
    BigDecimal rating;
    String popularityRange;

    //  método de limpieza para asegurarnos de que si un usuario quita los filtros, 
    // la aplicación se comporte como si nunca los hubiera puesto, mostrando todos los resultados de nuevo.
    public boolean isEmpty() {
        return (texto == null || texto.isBlank()) &&
                generoId == null &&
                year == null &&
                rating == null &&
                (popularityRange == null || popularityRange.isBlank());
    }
}
