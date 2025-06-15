package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dto.DTO;

import java.util.ArrayList;
import java.util.List;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors -
 */

public abstract class DTOService<DTOClass, EntityClass> {

    protected List<DTOClass> entity2DTO (List<EntityClass> entidades) {
        List<DTOClass> lista = new ArrayList<>();
        for (EntityClass entidad : entidades) {
            DTO<DTOClass> clase = (DTO<DTOClass>) entidad;
            lista.add(clase.toDTO());
        }
        return lista;
    }

}
