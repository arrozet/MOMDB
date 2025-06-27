package es.uma.taw.momdb.dto;

import lombok.Data;

/**
 * DTO genérico para manejar entidades simples con ID y nombre en la capa de administración.
 * Se utiliza para transferir datos de formularios de filtrado y edición,
 * permitiendo una gestión flexible de diferentes tipos de entidades (Genre, Keyword, etc.)
 * sin necesidad de crear un DTO específico para cada una en estos contextos.
 * 
 * @author arrozet (Rubén Oliva)
 */
@Data
public class GenericEntityDTO {
    /**
     * El tipo de entidad seleccionada (por ejemplo, "Genre", "Keyword").
     * Determina qué repositorio se utilizará en las operaciones de backend.
     */
    private String selectedEntity;
    /**
     * El término de búsqueda utilizado para filtrar las entidades por nombre.
     */
    private String filterName;
    /**
     * El ID de la entidad. Se usa String para soportar tanto IDs numéricos como de texto.
     */
    private String id;
    /**
     * El nombre o valor de la entidad.
     */
    private String name;
}
