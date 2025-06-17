package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dto.DTO;

import java.util.ArrayList;
import java.util.List;

/*
 * @author - Artur797 (Artur Vargas)
 * @co-authors -
 */

/**
 * Servicio abstracto para la conversión entre entidades y DTOs.
 * Se usa un límite en el tipo genérico (EntityClass extends DTO<DTOClass>) para garantizar en tiempo de compilación
 * que cualquier entidad usada con este servicio puede convertirse a su DTO. Esto evita conversiones
 * de tipo (castings) inseguras y hace el código más robusto.
 *
 * @param <DTOClass> La clase del Data Transfer Object.
 * @param <EntityClass> La clase de la entidad, que debe implementar la interfaz DTO.
 */
public abstract class DTOService<DTOClass, EntityClass extends DTO<DTOClass>> {

    protected List<DTOClass> entity2DTO (List<EntityClass> entidades) {
        List<DTOClass> lista = new ArrayList<>();
        for (EntityClass entidad : entidades) {
            lista.add(entidad.toDTO());
        }
        return lista;
    }

}
