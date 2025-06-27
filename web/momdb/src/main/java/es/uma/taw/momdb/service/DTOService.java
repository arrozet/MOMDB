package es.uma.taw.momdb.service;

import es.uma.taw.momdb.dto.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Servicio abstracto para la conversión entre entidades y DTOs.
 * Se usa un límite en el tipo genérico (EntityClass extends DTO<DTOClass>) para garantizar en tiempo de compilación
 * que cualquier entidad usada con este servicio puede convertirse a su DTO. Esto evita conversiones
 * de tipo (castings) inseguras y hace el código más robusto.
 *
 * @param <DTOClass> La clase del Data Transfer Object.
 * @param <EntityClass> La clase de la entidad, que debe implementar la interfaz DTO.
 * 
 * @author arrozet (Rubén Oliva, Javadocs - 52.9%), Artur797 (Artur Vargas - 47.1%)
 */
public abstract class DTOService<DTOClass, EntityClass extends DTO<DTOClass>> {

    /**
     * Convierte una lista de entidades a una lista de DTOs.
     * @param entidades Lista de entidades a convertir.
     * @return Lista de DTOs correspondientes.
     */
    protected List<DTOClass> entity2DTO (List<EntityClass> entidades) {
        List<DTOClass> lista = new ArrayList<>();
        for (EntityClass entidad : entidades) {
            lista.add(entidad.toDTO());
        }
        return lista;
    }

}
