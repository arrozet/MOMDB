package es.uma.taw.momdb.ui;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors -
 */

@Data
public class Filtro {
    String texto;
    List<Integer> generoIds;
}
