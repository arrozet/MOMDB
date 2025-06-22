package es.uma.taw.momdb.dto;

/*
 * @author arrozet (Rubén Oliva)
 */

public interface DTOWithNameAndId<T> {
    T getId();
    String getName();
} 